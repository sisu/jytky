#include <jni.h>
#include <errno.h>
#include <cstdlib>

#include <EGL/egl.h>
#include <GLES/gl.h>

#include <android/sensor.h>
#include <android/log.h>
#include <android_native_app_glue.h>
#include <string>
#include <jee/Log.hpp>
#include "Game.hpp"
using namespace std;

LOG_CHANNEL(main);

android_app* androidApp;

struct saved_state {
	int32_t x;
	int32_t y;
};

struct Engine {
	struct android_app* app;

	int animating;
	EGLDisplay display;
	EGLSurface surface;
	EGLContext context;
	int32_t width;
	int32_t height;
	struct saved_state state;

	string loadFile(const char* name) {
		AAssetManager* mgr = app->activity->assetManager;
		AAsset* f = AAssetManager_open(mgr, name, AASSET_MODE_STREAMING);
		char buf[1<<12];
		string s;
		while(1) {
			int len = AAsset_read(f, buf, sizeof(buf));
			if (!len) break;
			s.insert(s.end(), buf, buf+len);
		}
		AAsset_close(f);
		return s;
	}
};
Engine engine;

static void engine_init_display(Engine* engine) {
	const EGLint attribs[] = {
		EGL_SURFACE_TYPE, EGL_WINDOW_BIT,
		EGL_BLUE_SIZE, 8,
		EGL_GREEN_SIZE, 8,
		EGL_RED_SIZE, 8,
		EGL_NONE
	};

	EGLDisplay display = eglGetDisplay(EGL_DEFAULT_DISPLAY);

	eglInitialize(display, 0, 0);
	EGLConfig config;
	EGLint numConfigs;
	eglChooseConfig(display, attribs, &config, 1, &numConfigs);

	EGLint format;
	eglGetConfigAttrib(display, config, EGL_NATIVE_VISUAL_ID, &format);

	ANativeWindow_setBuffersGeometry(engine->app->window, 0, 0, format);

	const EGLint cAttrib[] = {
		EGL_CONTEXT_CLIENT_VERSION, 2,
		EGL_NONE
	};
	EGLSurface surface = eglCreateWindowSurface(display, config, engine->app->window, NULL);
	EGLContext context = eglCreateContext(display, config, NULL, cAttrib);

	if (eglMakeCurrent(display, surface, surface, context) == EGL_FALSE) {
		LOG<<"Unable to eglMakeCurrent";
		abort();
	}

	EGLint w, h;
	eglQuerySurface(display, surface, EGL_WIDTH, &w);
	eglQuerySurface(display, surface, EGL_HEIGHT, &h);

	engine->display = display;
	engine->context = context;
	engine->surface = surface;
	engine->width = w;
	engine->height = h;
}

float getTime() {
	struct timespec now;
	clock_gettime(CLOCK_MONOTONIC, &now);
	return now.tv_sec + now.tv_nsec/1e9;
}

static void engine_draw_frame(Engine* engine) {
	if (engine->display == NULL) {
		return;
	}

#if 0
	glClearColor(((float)engine->state.x)/engine->width, 0,
			((float)engine->state.y)/engine->height, 1);
	glClear(GL_COLOR_BUFFER_BIT);
#endif
	game.update(getTime());
	game.draw();

	eglSwapBuffers(engine->display, engine->surface);
}

static void engine_term_display(Engine* engine) {
	if (engine->display != EGL_NO_DISPLAY) {
		eglMakeCurrent(engine->display, EGL_NO_SURFACE, EGL_NO_SURFACE, EGL_NO_CONTEXT);
		if (engine->context != EGL_NO_CONTEXT) {
			eglDestroyContext(engine->display, engine->context);
		}
		if (engine->surface != EGL_NO_SURFACE) {
			eglDestroySurface(engine->display, engine->surface);
		}
		eglTerminate(engine->display);
	}
	engine->animating = 0;
	engine->display = EGL_NO_DISPLAY;
	engine->context = EGL_NO_CONTEXT;
	engine->surface = EGL_NO_SURFACE;
}

static int32_t engine_handle_input(struct android_app* app, AInputEvent* event) {
//	LOG<<"input"<<AInputEvent_getType(event);
	Engine* engine = (Engine*)app->userData;
	if (AInputEvent_getType(event) == AINPUT_EVENT_TYPE_MOTION) {
		engine->animating = 1;
		engine->state.x = AMotionEvent_getX(event, 0);
		engine->state.y = AMotionEvent_getY(event, 0);
		return 1;
	}
	return 0;
}

static void engine_handle_cmd(struct android_app* app, int32_t cmd) {
	LOG<<"cmd "<<cmd;
	Engine* engine = (Engine*)app->userData;
	switch (cmd) {
		case APP_CMD_SAVE_STATE:
			// TODO
			engine->app->savedState = 0;
			engine->app->savedStateSize = 0;
			break;
		case APP_CMD_INIT_WINDOW:
			if (engine->app->window != NULL) {
				engine_init_display(engine);
				LOG<<"game init";
				game.init(getTime());
				LOG<<"window init done";
				engine_draw_frame(engine);
			}
			break;
		case APP_CMD_TERM_WINDOW:
			engine_term_display(engine);
			break;
		case APP_CMD_GAINED_FOCUS:
			break;
		case APP_CMD_LOST_FOCUS:
			engine->animating = 0;
			engine_draw_frame(engine);
			break;
	}
}

void android_main(struct android_app* state) {
	app_dummy();
	androidApp = state;

	state->userData = &engine;
	state->onAppCmd = engine_handle_cmd;
	state->onInputEvent = engine_handle_input;
	engine.app = state;

	LOG<<engine.loadFile("t.vert");

#if 0
	if (state->savedState != NULL) {
		// We are starting with a previous saved state; restore from it.
		engine.state = *(struct saved_state*)state->savedState;
	}
#endif

	while (1) {
		int ident;
		int events;
		struct android_poll_source* source;

		while ((ident=ALooper_pollAll(engine.animating ? 0 : -1, NULL, &events,
						(void**)&source)) >= 0) {
			if (source != NULL) {
				source->process(state, source);
			}

			if (state->destroyRequested != 0) {
				engine_term_display(&engine);
				return;
			}
		}

		if (engine.animating) {
			engine_draw_frame(&engine);
		}
	}
}
