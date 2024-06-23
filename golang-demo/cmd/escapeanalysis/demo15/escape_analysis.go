package main

func main() {
	s1 := make([]*string, 0, 10)
	str1 := "str1"
	s1 = append(s1, &str1)

	s2 := make([]string, 0, 10)
	str2 := "str2"
	s2 = append(s2, str2)

	m1 := make(map[*string]string, 8)
	mkey1 := "mkey1"
	mvalue1 := "mvalue1"
	m1[&mkey1] = mvalue1

	m2 := make(map[string]*string, 8)
	mkey2 := "mkey2"
	mvalue2 := "mvalue2"
	m2[mkey2] = &mvalue2

	m3 := make(map[*string]*string, 8)
	mkey3 := "mkey3"
	mvalue3 := "mvalue3"
	m3[&mkey3] = &mvalue3

	m4 := make(map[string]string, 8)
	mkey4 := "mkey4"
	mvalue4 := "mvalue4"
	m4[mkey4] = mvalue4

	ch1 := make(chan *string, 10)
	chStr1 := "chStr1"
	ch1 <- &chStr1

	ch2 := make(chan string, 10)
	chStr2 := "chStr2"
	ch2 <- chStr2
}
