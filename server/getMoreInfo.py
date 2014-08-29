import re
import os
import sys
import linecache
import json
from Bio.Seq import Seq

def readEnzyme(the_dataset_file):
	""" read the enzyme dataset file from rebase """
	instruction = True
	enzymes_list = []
	i = 0
	with open(the_dataset_file, 'r') as infile:
		for line in infile:
			# if i > 40:
			# 	break
			if instruction:
				if line[:2] == "..":
					instruction = False
				continue
			enzyme_info = line.split()
			if enzyme_info[0][0] == ';':
				enzyme_info[0] = enzyme_info[0][1:]

			enzyme_info[1] = int(enzyme_info[1])
			enzyme_info[3] = int(enzyme_info[3])
			enzyme_info[2] = filerCutSiteMark(enzyme_info[2])
			enzyme_info[2] = changeToRE(enzyme_info[2])
			# print "line", i, ":::::", enzyme_info
			enzymes_list.append(enzyme_info)
			i = i + 1

		return enzymes_list

def filerCutSiteMark(seq):
	""" drop off the cut site mark like ' and _ """
	# drop off the mark '
	filtered = ""
	tmp = seq.split("'")
	filtered = filtered.join(tmp)

	# drop off the mark _
	tmp = filtered.split("_")
	filtered = ""
	filtered = filtered.join(tmp)
	return filtered
	
def changeToRE(seq):
	""" change anonymous type sequence to regular expression type """
	rules = {'A':'A', 'T':'T', 'C':'C', 'G':'G', \
			'r':'[GA]', 'y':'[CT]', 'm':'[AC]', 'k':'[GT]', 's':'[GC]', 'w':'[AT]',\
			'b':'[CGT]', 'd':'[AGT]', 'h':'[ACT]', 'v':'[ACG]',\
			'n':'.'}
	copy_seq = list(seq)
	index = 0
	for i in copy_seq:
		copy_seq[index] = rules[i]
		index = index + 1

	seq_toRE = "".join(copy_seq)
	# tmp_list = seq_toRE.split()
	# tmp_list.insert(0, '.*')
	# tmp_list.append('.*')
	# seq_toRE = "".join(tmp_list)
	return seq_toRE

def getMatched(seq, enzymes_list):
	""" get matched enzymes_list """
	matched_list = []
	for enzyme in enzymes_list:
		# compare the + strand
		matched_info = getMatchedInfo(seq, enzyme, '+')
		if matched_info != {}:
			matched_list.append(matched_info)
		# compare the - strand	
		seq_obj = Seq(seq)
		reverse_seq = str(seq_obj.reverse_complement())
		matched_info = getMatchedInfo(reverse_seq, enzyme, '-')
		if matched_info != {}:
			matched_list.append(matched_info)

	return matched_list

def getMatchedInfo(seq, enzyme, direction):
	""" check whether enzyme is matched to the seq of + strand """
	matched_info = dict()
	RE = re.compile(enzyme[2])
	RE_result = RE.search(seq)
	if RE_result != None:
		matched_seq = RE_result.group()
		start = RE_result.start()
		end = RE_result.end()
		matched_info = {\
			"enzyme_name": enzyme[0], \
			"matched_seq": matched_seq,\
			"start": start,\
			"end": end,\
			"strand": direction} 
	return matched_info

if __name__ == "__main__":
	enzymes_list = readEnzyme('gcg.txt')
	# seq = raw_input('Please input the sequence to get enzyme: ')
	seq = sys.argv[1]
	matched_list = getMatched(seq, enzymes_list)
	os.chdir('RNAfold')
	f = open(seq + '.txt', 'w')
	f.write('>'+seq+'\n'+seq)
	f.close()
	os.system("RNAfold < "+ seq +".txt > "+ seq +"_res.txt")
	os.system("convert "+seq+"_ss.ps "+seq+".jpg")
	resJson = {}
	resJson["enzyme"] = matched_list[:6]
	resJson["RNAfold-res"] = linecache.getline(seq+"_res.txt", 3)[:-1]
	resJson["RNAfold-img"] = seq+".jpg"
	print json.dumps(resJson)
