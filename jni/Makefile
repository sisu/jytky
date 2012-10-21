DIRS:=. render util sdl
SRC:=$(wildcard $(addsuffix /*.cpp,$(DIRS)))
OBJ:=$(patsubst %.cpp,obj/%.o,$(SRC))
ODIR:=obj
ODIRS:=$(addprefix $(ODIR)/, $(DIRS))
BIN:=jytky

BASEFLAGS:=-Wall -Wextra -std=c++0x -I. $(shell sdl-config --cflags) -MMD
DFLAGS:=-g
CXXFLAGS:=$(BASEFLAGS) $(DFLAGS)
#RENDERLIBS:=-L../render -lrender
#JEELIBS:=-L../jee/ -ljee
GLLIBS:=-lGL -lGLU
#LIBS:=$(shell sdl-config --libs) $(RENDERLIBS) $(JEELIBS) $(GLLIBS)
LIBS:=$(shell sdl-config --libs) $(GLLIBS)
#LIBS:=$(shell sdl-config --libs) -lGLESv2 -lGLU $(RENDERLIBS) $(JEELIBS)

.PHONY: all clean $(BIN)

all: $(ODIRS) $(BIN)

$(BIN): $(OBJ)
	g++ -o "$@" $(OBJ) $(CXXFLAGS) $(LIBS)

$(OBJ): $(ODIR)/%.o: %.cpp
	g++ "$<" -c -o "$@" $(CXXFLAGS)

clean:
	rm -rf "$(ODIR)"

$(ODIRS):
	mkdir -p "$@"

include $(wildcard $(ODIR)/*.d)
