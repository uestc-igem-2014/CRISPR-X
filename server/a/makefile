cc = g++
main = ../import
obj = main.o cJSON.o
x = -I../mysql-connector/include
y = -L../mysql-connector/lib -Wl,-dn -lmysqlclient -Wl,-dy -lm -lz -lcrypt -lpthread -ldl -lrt

all: $(obj)
	$(cc) -o $(main) $(obj) $(y)

cJSON.o : cJSON/cJSON.c cJSON/cJSON.h
	$(cc) -c cJSON/cJSON.c $(x)

main.o: main.cpp
	$(cc) -c main.cpp $(x)

.PHONY : clean
clean :
	rm $(obj)

.PHONY : cleanall
cleanall :
	rm $(obj) $(main)

