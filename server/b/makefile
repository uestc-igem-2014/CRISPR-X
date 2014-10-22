cc = g++
main = ../main
obj = main.o score.o cJSON.o region.o gene.o localresult.o util.o
x = -I ../mysql-connector/include
y = -L ../mysql-connector/lib -Wl,-dn -lmysqlclient -Wl,-dy -lm -lz -lcrypt -lpthread -ldl -lrt
util = util.h main.h cJSON/cJSON.h
CF = -c -fprofile-arcs -ftest-coverage
testcases = basic exception

.PHONY: clean cleanall test install beforeInstall $(testcases)

all: $(obj)
	$(cc) -o $(main) $(obj) $(y) -fprofile-arcs -ftest-coverage -lgcov

install: beforeInstall $(obj)
	$(cc) -o $(main) $(obj) $(y)

beforeInstall:
	$(eval CF = -c)

cJSON.o: cJSON/cJSON.c cJSON/cJSON.h
	$(cc) -c cJSON/cJSON.c $(x)

util.o: util.cpp $(util)
	$(cc) $(CF) util.cpp $(x)

main.o: main.cpp $(util)
	$(cc) $(CF) main.cpp $(x)

score.o: score.cpp $(util)
	$(cc) $(CF) score.cpp $(x)

region.o: region.cpp $(util)
	$(cc) $(CF) region.cpp $(x)

gene.o: gene.cpp $(util)
	$(cc) $(CF) gene.cpp $(x)

localresult.o: localresult.cpp $(util)
	$(cc) $(CF) localresult.cpp $(x)


clean:
	rm $(obj)

cleanall:
	rm $(obj) ../main *.gc*

test: $(testcases)

basic:
	echo "--------------test case basic 1 start-----------------"
	echo "basic test case, legal call"
	../main '{"specie":"Saccharomyces-cerevisiae","location":"NC_001134-chromosome2:200..2873","pam":"NGG","rfc":"100010"}'
	echo "---------------test case basic 1 end------------------"
	echo "--------------test case basic 2 start-----------------"
	../main '{"specie":"Saccharomyces-cerevisiae","gene":"ATP1","pam":"NGG","rfc":"011101"}'
	echo "---------------test case basic 2 end------------------"
	echo "--------------test case basic 3 start-----------------"
	../main '{"specie":"Saccharomyces-cerevisiae","gene":"ATP1","pam":"NGG","rfc":"011101","region":"1001","length":18}'
	echo "---------------test case basic 3 end------------------"


exception:
	# exception test, no NC_001134-chromosome3 record in Table_sgRNA
	echo "--------------test case exception 1 start-----------------"
	echo "exception test, call no-exist location"
	! ../main '{"specie":"Saccharomyces-cerevisiae","location":"NC_001135-chromosome3:200..2873","pam":"NGG","rfc":"100010"}'
	echo "---------------test case exception 1 end------------------"

	echo "--------------test case exception 2 start-----------------"
	echo "exception test, call no-exist gene"
	../main '{"specie":"Saccharomyces-cerevisiae","gene":"XXXXX","pam":"NGG","rfc":"011101"}'
	echo "--------------test case exception 2 end -----------------"

	echo "--------------test case exception 3 start-----------------"
	echo "query in illegal rfc combinations"
	# here, RFC 10 is incompatible to BioBrick RFC 12 and RFC 23
	../main '{"specie":"Saccharomyces-cerevisiae","location":"NC_001134-chromosome2:200..2873","pam":"NGG","rfc":"110010"}'
	echo "--------------test case exception 3 end -----------------"

	echo "--------------test case exception 4 start-----------------"
	echo "exception test, wrong args"
	../main hello world
	echo "---------------test case exception 4 end------------------"

	echo "--------------test case exception 5 start-----------------"
	echo "exception test, invalid nt length"
	../main '{"specie":"Saccharomyces-cerevisiae","gene":"ATP1","pam":"NGG","rfc":"011101,"length":16}'
	echo "---------------test case exception 5 end------------------"
