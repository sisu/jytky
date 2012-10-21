#include "modelgen.hpp"

ModelPtr makeCube() {
	ModelPtr m(new Model);
	Vec3 vts[] = {
		{-1,-1,-1},
		{1,-1,-1},
		{1,1,-1},
		{-1,1,-1},
		{-1,-1,1},
		{1,-1,1},
		{1,1,1},
		{-1,1,1},
	};
	int n = sizeof(vts)/sizeof(vts[0]);
	m->setAttr("pos", vts, n);
	for(int i=0; i<n; ++i) vts[i] = normalize(vts[i]);
	m->setAttr("normal", vts, n);
	short idx[6][4] = {
		{0,1,2,3},
		{4,5,6,7},
		{0,1,5,4},
		{2,3,7,6},
		{0,4,7,3},
		{1,5,6,2},
	};
	for(int i=0; i<6; ++i) {
		short* s = idx[i];
		m->addTriangle(s[0],s[1],s[2]);
		m->addTriangle(s[0],s[2],s[3]);
	}
	return m;
}
