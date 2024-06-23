package main

import "unsafe"

type MyInterface interface {
	Do()
}

type Pointer struct {
}

type MyStruct struct {
	FieldA string
}

func (s *Pointer) Do() {

}

func (s MyStruct) Do() {

}

func Pointer2MyInterface(s *Pointer) MyInterface {
	return s
}

func MyStruct2MyInterface(s MyStruct) MyInterface {
	return s
}

type Type interface {
	Kind()
}

type rtype struct {
}

type emptyInterface struct {
	typ  *rtype
	word unsafe.Pointer
}

func (t *rtype) Kind() {

}

func toType(t *rtype) Type {
	return t
}

func TypeOf(i interface{}) Type {
	eface := *(*emptyInterface)(unsafe.Pointer(&i))
	return toType(eface.typ)
}

func MyPointerTypeOf(i interface{}) MyInterface {
	p := (*Pointer)(unsafe.Pointer(&i))
	return Pointer2MyInterface(p)
}

func main() {
	p := &Pointer{}
	Pointer2MyInterface(p)

	// Pointer2MyInterface(p).Do()

	s1 := MyStruct{}
	MyStruct2MyInterface(s1)
	MyStruct2MyInterface(s1).Do()

	str := "Crayon"
	s2 := MyStruct{
		FieldA: str,
	}
	MyStruct2MyInterface(s2)
	MyStruct2MyInterface(s2).Do()

	pp := &Pointer{}
	TypeOf(pp)

	TypeOf(pp).Kind()

	TypeOf(pp).(*rtype).Kind()

	ppp := Pointer{}
	TypeOf(ppp)

	TypeOf(ppp).Kind()

	TypeOf(ppp).(*rtype).Kind()

	p1 := Pointer{}
	MyPointerTypeOf(p1)

}
