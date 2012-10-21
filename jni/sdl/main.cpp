#include <SDL.h>
#include "Game.hpp"

void handleKeys() {
	SDL_PumpEvents();
	Uint8* k = SDL_GetKeyState(0);
	game.player.move[0] = k[SDLK_RIGHT] - k[SDLK_LEFT];
	game.player.move[1] = k[SDLK_UP] - k[SDLK_DOWN];
//	game.player.move[1] = k[SDLK_SPACE];
}

void mainLoop() {
	bool end=0;
	while(!end) {
		SDL_Event e;
		while(SDL_PollEvent(&e)) {
			if (e.type==SDL_QUIT) end=1;
			else if (e.type==SDL_KEYDOWN) {
				SDLKey k = e.key.keysym.sym;
				if (k==SDLK_ESCAPE) end=1;
			} else if (e.type==SDL_KEYUP) {
			}
		}
		handleKeys();
		float t = SDL_GetTicks()/1000.;
		game.update(t);
		game.draw();
		SDL_GL_SwapBuffers();
		SDL_Delay(10);
	}
}

int main(/*int argc, char* argv[]*/) {
	SDL_Init(SDL_INIT_VIDEO | SDL_INIT_TIMER);
	atexit(SDL_Quit);
	SDL_SetVideoMode(800, 600, 0, SDL_OPENGL);
	game.init(SDL_GetTicks()/1000.);
	mainLoop();
}
