#pragma once

class Noncopyable {
	Noncopyable(const Noncopyable&);
	Noncopyable& operator=(const Noncopyable&);
protected:
	Noncopyable() {}
};
