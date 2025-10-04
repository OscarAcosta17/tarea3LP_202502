# Variables
SRC_DIR = .
OUT_DIR = out

SOURCES := $(shell find $(SRC_DIR) -name "*.java")
CLASSES := $(SOURCES:$(SRC_DIR)/%.java=$(OUT_DIR)/%.class)

all: run

compile: $(CLASSES)

$(OUT_DIR)/%.class: $(SRC_DIR)/%.java
	@mkdir -p $(dir $@)
	javac -d $(OUT_DIR) $<

run: compile
	java -cp $(OUT_DIR) Main

clean:
	rm -rf $(OUT_DIR)
