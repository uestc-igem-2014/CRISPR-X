import linecache
import MySQLdb
import os

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

def getPAMs():
	tmp_set = set()
	tmp_set.add('NGG')
	tmp_set.add('NRG')
	tmp_set.add('NNNNGMTT')
	tmp_set.add('NNAGAAW')
	return list(tmp_set)

if __name__ == "__main__":
	# species = 'E.coli K12-DH10B'
	# chromoesome = 'NC_010473'
	# chromoesome = 'NC_001148-chromosome16'
	db = MySQLdb.connect("localhost","root","iloveweb","CasDB" )
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
		arg1='{"files":['
		mark=0;

		chromosome_list = getChromoesomeList(os.listdir('.'))
		for chromosome in chromosome_list:
                        if(mark==1) arg1+=','
                        mark=1
			arg1+='{"fileName"="'+chromosome+'","filePath":"'+chromosome+'.fna"}'
		os.chdir('..')

		pam_list = getPAMs()
		for pam in pam_list:
			prog='../../upload/import \''+arg1+'],"PAM":"'+pam+'"}\' 0'
			os.system(prog)



	# ChrLength = getChrLength(species, chromoesome)
	# print ChrLength
	print "==================== END ======================"
	# print getItems(species, chromoesome, ChrLength)
	db.close()
