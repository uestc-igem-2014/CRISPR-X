cc = g++
main = ../main
obj = main.o score.o cJSON.o region.o gene.o localresult.o util.o
x = -I ../mysql-connector/include
y = -L ../mysql-connector/lib -Wl,-dn -lmysqlclient -Wl,-dy -lm -lz -lcrypt -lpthread -ldl -lrt
util = util.h main.h cJSON/cJSON.h
CF = -c -fprofile-arcs -ftest-coverage
testcases = basic

all: $(obj)
	$(cc) -o $(main) $(obj) $(y) -fprofile-arcs -ftest-coverage -lgcov

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

.PHONY: clean cleanall test $(testcases)

clean:
	rm $(obj)

cleanall:
	rm $(obj) ../main

test: $(testcases)

basic:
	../main '{"specie":"Saccharomyces-cerevisiae","location":"NC_001134-chromosome2:200..2873","pam":"NGG","rfc":"100010"}'
