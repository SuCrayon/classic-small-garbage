package main

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

func main() {
	p := &Pointer{}
	Pointer2MyInterface(p)
	Pointer2MyInterface(p).Do()
	Pointer2MyInterface(p).(*Pointer).Do()

	s1 := MyStruct{}
	MyStruct2MyInterface(s1)
	MyStruct2MyInterface(s1).Do()

	str := "Crayon"
	s2 := MyStruct{
		FieldA: str,
	}
	MyStruct2MyInterface(s2)
	MyStruct2MyInterface(s2).Do()

}
