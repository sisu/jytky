#pragma once
#include "assign.hpp"

template<class T, int N>
struct Vector {
	Vector() {}

	typedef T type;
	enum { size = N };

	template<class...A>
	Vector(A... args) {
		static_assert(sizeof...(A)==N, "Wrong number of initializer arguments.");
		assign(data, args...);
	}
	static Vector zero() {
		Vector v;
		for(int i=0; i<N; ++i) v[i]=0;
		return v;
	}

	T& operator[](int i) { return data[i]; }
	const T& operator[](int i) const { return data[i]; }

	T data[N];
};

typedef Vector<float,2> Vec2;
typedef Vector<float,3> Vec3;
typedef Vector<float,4> Vec4;
