package main

import (
	"reflect"
)

type MyInterface interface {
	Get() string
}

type DefaultMyInterface struct {
	Field string
}

func (d DefaultMyInterface) Get() string {
	return d.Field
}

func NewMyInterface(s string) MyInterface {
	return DefaultMyInterface{
		Field: s,
	}
}

func dynamicType(v interface{}) interface{} {
	return v
}

func dynamicTypeAssert(v interface{}) interface{} {
	_, _ = v.(string)
	return v
}

func dynamicTypeReflect(v interface{}) interface{} {
	reflect.TypeOf(v).Kind()
	return v
}

func main() {
	s := "Crayon"
	// 普通动态类型入参不会发生逃逸
	dynamicType(s)
	// 断言
	dynamicTypeAssert(s)
	// 反射
	dynamicTypeReflect(s)

	//dmi := &DefaultMyInterface{}
	//dmi.Get()
	//mi := MyInterface(dmi)
	//mi.Get()

	ss := "ss"
	mi := NewMyInterface(ss)
	mi.Get()

	//i := DefaultMyInterface{}
	//_ = unsafe.Pointer(&i)
}
