#include "Log.hpp"

Log::Log(std::string channel) {
	ignore = 0;
	// TODO
//	if (channel=="gjk") ignore=1;
	ss << channel << ": ";
}
