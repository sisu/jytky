#pragma once

#include <sstream>
#include "alog.hpp"

struct Log {
	template<class T>
	Log& operator<<(const T& t) {
		ss << t;
		return *this;
	}
	~Log() {
		if (!ignore) {
			LOGI("%s\n", ss.str().c_str());
			fflush(stdout);
		}
	}
	Log(): ignore(0) {}
	Log(std::string channel);

private:
	std::ostringstream ss;
	bool ignore;
};

#define LOG_CHANNEL(name) static std::string logChannel = #name
#define LOG Log(logChannel)
