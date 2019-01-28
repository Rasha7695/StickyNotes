SRC = ./src/Main
OUT = ./out

all:
	javac -d $(OUT) $(SRC)/*.java

clean:
	rm -rf $(OUT)/*.class