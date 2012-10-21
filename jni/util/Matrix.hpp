#pragma once

#include "Vector.hpp"

template<class A>
struct Assign {};

template<class T, int N>
struct Matrix {
	// column-Vectors
	Vector<T,N> data[N];
	Vector<T,N>& operator[](int i) { return data[i]; }
	const Vector<T,N>& operator[](int i) const { return data[i]; }

	T* ptr() { return &data[0][0]; }
	const T* ptr() const { return &data[0][0]; }

	template<class A>
	Matrix& operator=(const Assign<A>& a) {
		static_cast<const A&>(a)(*this);
		return *this;
	}
	Matrix() {}
	template<class A>
	Matrix(const Assign<A>& a) {
		static_cast<const A&>(a)(*this);
	}

	void transpose() {
		for(int i=0; i<N-1; ++i)
			for(int j=i+1; j<N; ++j)
				swap(data[i][j], data[j][i]);
	}
};

typedef Matrix<float, 4> Matrix4;
typedef Matrix<float, 3> Matrix3;

#define OP(op,ope)\
	template<class T, int N> inline Matrix<T,N>& operator ope(Matrix<T,N>& a, const Matrix<T,N>& b) { for(int i=0; i<N; ++i) a[i] ope b[i]; return a; }\
	template<class T, int N> inline Matrix<T,N> operator op(Matrix<T,N> a, const Matrix<T,N>& b) { return a ope b; }
OP(+,+=)
OP(-,-=)
#undef OP

#define OP(op,ope)\
	template<class T, int N> inline Matrix<T,N>& operator ope(Matrix<T,N>& a, T x) { for(int i=0; i<N; ++i) a[i] ope x; return a; }\
	template<class T, int N> inline Matrix<T,N> operator op(Matrix<T,N> a, T x) { return a ope x; }
OP(*,*=)
OP(/,/=)
#undef OP

struct Zero: Assign<Zero> {
	template<class T, int N>
	void operator()(Matrix<T,N>& m) const {
		for(int i=0; i!=N; ++i)
			for(int j=0; j!=N; ++j)
				m[i][j] = 0;
	}
};
struct Identity: Assign<Identity> {
	template<class T, int N>
	void operator()(Matrix<T,N>& m) const {
		m = Zero();
		for(int i=0; i<N; ++i) m[i][i] = 1;
	}
};
template<int N>
struct Translate: Assign<Translate<N> > {
	Translate(Vector<float,N> v): v(v) {}
	Vector<float,N> v;
	void operator()(Matrix4& m) const {
		m = Identity();
		for(int i=0; i<N; ++i) m[3][i] = v[i];
	}
};
template<int N>
struct Scale: Assign<Scale<N> > {
	Scale(Vector<float,N> v): v(v) {}
	Vector<float,N> v;
	void operator()(Matrix4& m) const {
		m = Zero();
		for(int i=0; i<N; ++i) m[i][i] = v[i];
		for(int i=N; i<4; ++i) m[i][i]=1;
	}
};

template<int N>
Translate<N> translate(Vector<float,N> v) {
	return Translate<N>(v);
}
template<class...A>
Translate<sizeof...(A)> translate(A... args) {
	return Translate<sizeof...(A)>(Vector<float,sizeof...(A)>(args...));
}
template<int N>
Scale<N> scale(Vector<float,N> v) {
	return Scale<N>(v);
}
template<class...A>
Scale<sizeof...(A)> scale(A... args) {
	return Scale<sizeof...(A)>(Vector<float,sizeof...(A)>(args...));
}

// TODO: more generic
template<class...A>
Matrix4 initMatrix(A... args) {
	static_assert(sizeof...(A)==16, "Wrong number of initializer arguments.");
	Matrix4 m;
	assign(m.ptr(), args...);
	return m;
}

template<class T, int N>
Matrix<T,N> transpose(Matrix<T,N> m) { m.transpose(); return m; }


template<class T, int N>
Matrix<T,N> operator*(const Matrix<T,N>& a, const Matrix<T,N>& b) {
	Matrix<T,N> r = Zero();
	for(int i=0; i<N; ++i)
		for(int j=0; j<N; ++j)
			r[i] += a[j] * b[i][j];
	return r;
}

template<class T, int N>
Vector<T,N> operator*(const Matrix<T,N>& a, const Vector<T,N>& v) {
	Vector<T,N> r = Zero();
	for(int i=0; i<N; ++i)
		r += v[i]*a[i];
	return r;
}
