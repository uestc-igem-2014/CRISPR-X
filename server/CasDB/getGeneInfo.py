'''
# How To
## 1. get gene_table 
(chromoesome_name, gene_name, start, end, strand, UTR_location)

## 2. get intergenic_table
(chromoesome_name, start, end)

## 3. get exon_table
(chromoesome_name, gene_name, start, end, strand, length, PID)

## 4. get intron_table
(chromoesome_name, gene_name, start, end, strand)
> ?? for intron, how to get strand

> First, get the total length of the chromoesome from fna
> Read ffn and ptt accordingly.
> from ptt, get the gene_table item, the intergenic_table item and record the gnen_id
> from ffn, get serveral exon_table item and deduce the intron_table item

>>> Attention!
consider the the ptt and ffn are in same order, so 
we can read these two files accordingly
'''

import linecache
import MySQLdb
import os

def getChrLength(chromoesome_name):
	""" get the chromoesome length
	
	Args:
		chromoesome_name, the name of target chromoesome
	Returns:
		the length of the chromoesome
	Raises:
		None
	"""
	filename = chromoesome_name + '.fna'

	lineNum = sum(1 for line in open(filename))

	firstLine = linecache.getline(filename, 2)
	lineLen = len(firstLine[:-1])

	lastLine = linecache.getline(filename, lineNum)
	lastLen = len(lastLine[:-1])

	ChrLength = lineLen * (lineNum-2) + lastLen
	return ChrLength

def fillTables(chr_id, chromoesome_name, chromoesome_length, db, db_cursor):
	""" fill the calculated consequence into the corresponding tables
	
	Args:
		chr_id, the chromoesome ID 
		chromoesome_name, the chromoesome name
		chromoesome_length, the length of the chromoesome
		db, the MySQL entry
		db_cursor, the MySQL cursor using to commit or rollback
	Returns:
		None
	Raises:
		None
	"""
	ptt = chromoesome_name + '.ptt'
	ffn = chromoesome_name + '.ffn'
	instru_lines = 3
	with open(ptt) as pttF, open(ffn) as ffnF:
		for line_ptt in pttF:
			# i = i -1
			if instru_lines > 0:
				instru_lines = instru_lines -1
				continue

			info_gene = line_ptt.split('\t')
			info_gene[0] = info_gene[0].split('..')
			print info_gene
			# save to gene_table
			gene_table_item = {
				"gene_name": info_gene[4],
				"strand": info_gene[1],
				"start": info_gene[0][0],
				"end": info_gene[0][1]
			}
			gene_ID = insertGeneTable(Chr_No, gene_table_item, db, db_cursor)

			# get this item seq line num in ffn
			seq_len = (int(info_gene[2]) + 1 )*3
			item_seq_lines = seq_len / 70
			if seq_len % 70 != 0:
				item_seq_lines = item_seq_lines + 1
			seq = ''
			count = 0
			info_locs = []
			ifreceive = True
			for line_ffn in ffnF:
				# print line_ffn
				# print line_ptt
				if count < item_seq_lines:
					if line_ffn[0] == '>':
						# print line_ffn
						info_list = line_ffn.split('|')
						info_locs = info_list[4].split(' ')[0].split(',')
						info_locs[0] = info_locs[0][1:]
						# print "@@@ info_locs", info_locs
						strand = '+'
						exon_list = []
						intron_list = []
						if not checkCorresponding(info_locs, info_gene[0][0], info_gene[0][1]):
							ifreceive = False
							continue
						ifreceive = True
						if info_locs[0][0] == 'c':
							# this gene is in - strand
							exon_list, intron_list = getNegStrandIE(info_locs) 
							strand = '-'
						else:
							exon_list, intron_list = getPosStrandIE(info_locs)
						# save to exon_table
						# print "@@@@ exon_list", exon_list
						# print "@@@@ intron_list", intron_list
						exon_table_item = {
							# 'gene_name': info_gene[4],
							'locs': exon_list,
							'strand': strand,
							'PID': info_gene[3]
						}
						# print "!!!!!!!! exon_table_item", exon_table_item 
						insertRegionTable(gene_ID, 1, exon_table_item, db, db_cursor)
						intron_table_item = {
							# 'gene_name': info_gene[4],
							'locs': intron_list,
							'strand': strand,
							'PID': info_gene[3]
						}
						# print "!!!!!!!! intron_table_item", intron_table_item
						insertRegionTable(gene_ID, 2, intron_table_item, db, db_cursor)
					else:
						if ifreceive:
							seq = seq + line_ffn[:-1]

					if ifreceive:
						count = count + 1
				else:
					seq = seq + line_ffn[:-1]
					# print seq
					# do calculate here
					UTR_loc_Front = findUTRFont(seq)
					UTR_loc_End = findUTREnd(seq, seq_len)
					gene_table_item['UTR'] = [UTR_loc_Front, UTR_loc_End]
					insertUTR(gene_ID, UTR_loc_Front, UTR_loc_End, db, db_cursor)
					break

			# if i < 0:
				# break

def findNonzeroMin(num_list):
	""" find the minimum nonzero number in a list
	
	Args:
		num_list, a list of number
	Returns:
		the minimum nonzero number
	Raises:
		None
	"""
	tmp_list = [i for i in num_list if i>0]
	if tmp_list == []:
		return -1
	else:
		return min(tmp_list)

def findUTRFont(seq):
	""" get the font UTR location of the sequence
	
	Args:
		seq, the target sequence, a string
	Returns:
		the end location of the font UTR, if no font UTR, return 0
	Raises:
		None
	"""
	UTR_loc_Front = 0
	begin_list = []
	begin_list.append(seq.find('TAC'))
	begin_list.append(seq.find('CAC'))
	begin_min = findNonzeroMin(begin_list)
	if begin_min > 0:
		UTR_loc_Front = begin_min
	return UTR_loc_Front

def findUTREnd(seq, seq_len):
	""" get the end UTR location of the sequence
	
	Args:
		seq, the target sequence, a string
		seq_len, the length of the whole gene sequence
	Returns:
		the begin location of the end UTR
	Raises:
		None
	"""
	UTR_loc_End = 0
	end_list = []
	end_list.append(seq_len - seq.rfind('ATT'))
	end_list.append(seq_len - seq.rfind('ACT'))
	end_list.append(seq_len - seq.rfind('ATC'))
	end_min = findNonzeroMin(end_list)
	if end_min > 0:
		UTR_loc_End = end_min
	return UTR_loc_End

def getNegStrandIE(elist):
	""" get the inner region information of gene on negative strand
	
	Args:
		elist, a string from corresponding ffn file, showing the regional information
	Returns:
		exon_list, a list representing the information of exons, e.g. [(begin_1, end_1), (..), ..]
		intron_list, a list representing the information of introns
	Raises:
		None
	"""
	tmp_list = [i[1:].split('-') for i in elist]
	tmp_list = [( int(i[1]), int(i[0]) ) for i in tmp_list]
	exon_list = [i for i in reversed(tmp_list)]

	intron_list = []
	tag = exon_list[0][0]
	for i in exon_list:
		if tag == i[0]:
			pass
		else:
			intron_list.append( [tag, i[0]-1] )
		tag = i[1] + 1
	return exon_list, intron_list

def getPosStrandIE(elist):
	""" get the inner region information of gene on positive strand
	
	Args:
		elist, a string from corresponding ffn file, showing the regional information
	Returns:
		exon_list, a list representing the information of exons, e.g. [(begin_1, end_1), (..), ..]
		intron_list, a list representing the information of introns
	Raises:
		None
	"""
	tmp_list = [i.split('-') for i in elist]
	exon_list = [( int(i[0]), int(i[1]) ) for i in tmp_list]

	intron_list = []
	tag = exon_list[0][0]
	for i in exon_list:
		if tag == i[0]:
			pass
		else:
			intron_list.append( [tag, i[0]-1] )
		tag = i[1] + 1
	return exon_list, intron_list

def getChromoesomeList(file_list):
	""" get all chromoesomes from the species directory
	
	Args:
		file_list, the filename list of a certainty species
	Returns:
		a list representing the chromoesomes of the certainty species
	Raises:
		None
	"""
	tmp_list = [f[:-4] for f in file_list if os.path.isfile(f)]
	tmp_set = set()
	for i in tmp_list:
		tmp_set.add(i)
	return list(tmp_set)

def insertChrTable(species_id, species_name, chromoesome_name, db, db_cursor):
	""" insert a chromoesome information into Table_chromosome
	
	Args:
		species_id, the ID of the species in Table_Specie
		species_name, the name of the species in Table_Specie
		chromoesome_name, the name of the chromoesome
		db, the MySQL entry
		db_cursor, the MySQL cursor using to commit or rollback
	Returns:
		None
	Raises:
		None
	"""
	addr = species_name + '/' + chromoesome_name
	sql = 'INSERT INTO `Table_chromosome` (`Sno`, `Chr_Name`, `Chr_Address`) VALUES('+\
			str(species_id)+ ', "'+ chromoesome_name+'", "' +addr+'")'
	print "++++++++++++++++++"
	print sql
	try:
		db_cursor.execute(sql)
		db.commit()
	except:
		print "---------------INSERT Failed"
		db.rollback()
		raise

	sql = 'SELECT `Chr_No` FROM `Table_chromosome` WHERE `Chr_Address` = "'+ addr + '"'
	db_cursor.execute(sql)
	Chr_No = db_cursor.fetchone()
	return int(Chr_No[0])

def insertGeneTable(Chr_No, gene_info, db, db_cursor):
	""" insert gene information into Table_gene
	
	Args:
		Chr_No, the chromoesome No. that this gene belongs to 
		gene_info, the detailed gene information, containing gene name, gene start position, 
			gene end position, gene strand.
		db, the MySQL entry
		db_cursor, the MySQL cursor using to commit or rollback
	Returns:
		None
	Raises:
		None
	"""
	sql = 'INSERT INTO `Table_gene` (`Chr_No`, `gene_Name`, `gene_Start`, \
		`gene_end`, `gene_Strand`) VALUES('+\
		str(Chr_No)+', "'+ gene_info['gene_name'] +'", '+ gene_info['start'] +\
		', '+ gene_info['end'] + ', "'+ gene_info['strand'] + '")'
	print "++++++++++++++++++"
	print sql
	try:
		db_cursor.execute(sql)
		db.commit()
	except:
		print "---------------INSERT Failed"
		db.rollback()
		raise
	sql = 'SELECT `gene_ID` FROM `Table_gene` WHERE `gene_Start` ='+ gene_info['start']\
		+ ' AND `gene_end` =' + gene_info['end'] + ' AND `Chr_No` =' + str(Chr_No) 
	print sql
	db_cursor.execute(sql)
	gene_ID = db_cursor.fetchone()
	print gene_ID[0]
	return int(gene_ID[0])

def insertUTR(gene_ID, UTR_loc_Front, UTR_loc_End, db, db_cursor):
	""" insert UTR information of a gene into Table_region
	
	Args:
		gene_ID, the ID of the gene in Table_gene
		UTR_loc_Front, the end position of the font UTR in the gene
		UTR_loc_End, the begin position of the end UTR in the gene
		db, the MySQL entry
		db_cursor, the MySQL cursor using to commit or rollback
	Returns:
		None
	Raises:
		None
	"""
	sql = 'UPDATE `Table_gene` SET `gene_UTRstart`='+str(UTR_loc_Front)+','+\
		'`gene_UTRend`='+str(UTR_loc_End)+' WHERE `gene_ID`='+str(gene_ID)
	
	print "++++++++++++++++++"
	print sql
	try:
		db_cursor.execute(sql)
		db.commit()
	except:
		print "---------------INSERT Failed"
		db.rollback()
		raise

def insertRegionTable(gene_ID, rtd, region, db, db_cursor):
	""" insert regional information (exon or intron) into Table_region
	
	Args:
		gene_ID, the ID of the gene in Table_gene
		rtd, the regional type ID, for exon, it's 1, for intron, it's 2
		region, a list, the detailed information, containing start position, end position, strand, length
		db, the MySQL entry
		db_cursor, the MySQL cursor using to commit or rollback
	Returns:
		None
	Raises:
		None
	"""
	for i in region['locs']:
		sql = 'INSERT INTO `Table_region` (`rtd_id`, `gene_ID`, `region_start`, `region_end`,\
			`region_strand`, `region_length`, `region_PID`) VALUES('+\
			str(rtd)+', '+str(gene_ID) + ', '+str(i[0]) + ', '+str(i[1])+', "'\
			+region['strand'] + '", '+ str(i[1]-i[0]+1) + ', '+ region['PID'] +')'
		print "++++++++++++++++++"
		print sql
		try:
			db_cursor.execute(sql)
			db.commit()
		except:
			print "---------------INSERT Failed"
			db.rollback()
			raise

def checkCorresponding(info_locs, gene_start, gene_end):
	""" check whether information in ffn file is corresponding to the information in ptt file
	
	Args:
		info_locs, the locals information in ffn
		gene_start & gene_end, the locals information in ptt
	Returns:
		if it is corresponding, return True, else return False
	Raises:
		None
	"""
	tmp_list = []
	alist = []
	if info_locs[0][0] == 'c':
		tmp_list = [i[1:].split('-') for i in info_locs]
	else:
		tmp_list = [i.split('-') for i in info_locs]

	for i in tmp_list:
		alist.append( int(i[0]) )
		alist.append( int(i[1]) )
	if min(alist) == int(gene_start) and max(alist) == int(gene_end):
		return True
	else:
		return False



if __name__ == "__main__":
	# species = 'E.coli K12-DH10B'
	# chromoesome = 'NC_010473'
	# chromoesome = 'NC_001148-chromosome16'
	db = MySQLdb.connect("localhost","root","iloveweb","test" )
	db_cursor = db.cursor()
	sql = 'select * from Table_Specie'
	db_cursor.execute(sql)
	species_list = db_cursor.fetchall()
	species_list = [i[1] for i in species_list]
	print species_list

	# change directory into database
	os.chdir('database/')
	for index, species in enumerate(species_list):
		index = index + 1
		os.chdir(species)

		chromoesome_list = getChromoesomeList(os.listdir('.'))
		print index, species
		print chromoesome_list
		for chromoesome in chromoesome_list:
			ChrLength = getChrLength(chromoesome)
			print 'chromoesome_length:::::', ChrLength
			Chr_No = insertChrTable(index, species, chromoesome, db, db_cursor)
			print Chr_No
			fillTables(Chr_No, chromoesome, ChrLength, db, db_cursor)
		os.chdir('..')



	# ChrLength = getChrLength(species, chromoesome)
	# print ChrLength
	print "==================== END ======================"
	# print getItems(species, chromoesome, ChrLength)
	db.close()

