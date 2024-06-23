package main

import "reflect"

func main() {
	s := "Crayon"
	reflect.TypeOf(s)
	reflect.TypeOf(s).Kind()
	reflect.ValueOf(s)
	reflect.ValueOf(s).Kind()
}
