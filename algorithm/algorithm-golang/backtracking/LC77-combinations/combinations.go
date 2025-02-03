package LC77_combinations

func recurve(idx int, n int, k int, cur *[]int, res *[][]int) {
	if len(*cur) == k {
		tmp := make([]int, len(*cur))
		copy(tmp, *cur)
		*res = append(*res, tmp)
		return
	}
	for i := idx + 1; i <= n; i++ {
		*cur = append(*cur, i)
		recurve(i, n, k, cur, res)
		*cur = (*cur)[:len(*cur)-1]
	}
}

func Combine(n int, k int) [][]int {
	res := make([][]int, 0)
	cur := make([]int, 0, k)
	recurve(0, n, k, &cur, &res)
	return res
}
