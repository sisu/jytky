#include "file.hpp"
#include "Log.hpp"
#ifdef ANDROID
#include <android_native_app_glue.h>
#else
#include <cstdio>
#endif
using namespace std;
LOG_CHANNEL(file);

string loadFile(const char* name) {
#ifdef ANDROID
	extern android_app* androidApp;
	AAssetManager* mgr = androidApp->activity->assetManager;
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
#else
	// FIXME: define somewhere?
	string fname = string("../assets/")+name;
	FILE* f = fopen(fname.c_str(), "r");
	char buf[1<<12];
	string s;
	if (!f) {
		LOG<<"Failed reading file "<<name;
		abort();
	}
	while(1) {
		int len = fread(buf, 1, sizeof(buf), f);
		if (!len) break;
		s.insert(s.end(), buf, buf+len);
	}
	fclose(f);
	return s;
#endif
}
