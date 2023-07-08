package compare_version_numbers

/*func VersionCompare(v1, v2 string) int {
	var (
		slice1 = strings.Split(v1, ".")
		slice2 = strings.Split(v2, ".")
	)

	for i := 0;i < len(slice1) || i < len(slice2);i++ {
		x := 0
		y := 0

		if i < len(slice1) {
			// 这里的err需要根据实际情况做异常处理
			x, _ = strconv.Atoi(slice1[i])
		}

		if i < len(slice2) {
			// 这里的err需要根据实际情况做异常处理
			y, _ = strconv.Atoi(slice2[i])
		}

		if x > y {
			return 1
		}
		if x < y {
			return -1
		}
	}

	return 0
}*/

func VersionCompare(v1, v2 string) int {
	var (
		i = 0
		j = 0
	)
	for ; i < len(v1) || j < len(v2); {
		var (
			x = 0
			y = 0
		)
		for ; i < len(v1) && v1[i] != '.'; i++ {
			x = 10*x + int(v1[i]-'0')
		}
		i++
		for ; j < len(v2) && v2[j] != '.'; j++ {
			y = 10*y + int(v2[j]-'0')
		}
		j++

		if x > y {
			return 1
		}
		if x < y {
			return -1
		}
	}
	return 0
}

/*func VersionCompareWithCommitID(v1, v2 string) int {
}*/
