package LC17_letter_combinations_of_a_phone_number

const (
	CHAR_ONE   = '1'
	CHAR_TWO   = '2'
	CHAR_THREE = '3'
	CHAR_FOUR  = '4'
	CHAR_FIVE  = '5'
	CHAR_SIX   = '6'
	CHAR_SEVEN = '7'
	CHAR_EIGHT = '8'
	CHAR_NINE  = '9'
)

var (
	ONE_LETTERS   = []uint8{}
	TWO_LETTERS   = []uint8{'a', 'b', 'c'}
	THREE_LETTERS = []uint8{'d', 'e', 'f'}
	FOUR_LETTERS  = []uint8{'g', 'h', 'i'}
	FIVE_LETTERS  = []uint8{'j', 'k', 'l'}
	SIX_LETTERS   = []uint8{'m', 'n', 'o'}
	SEVEN_LETTERS = []uint8{'p', 'q', 'r', 's'}
	EIGHT_LETTERS = []uint8{'t', 'u', 'v'}
	NINE_LETTERS  = []uint8{'w', 'x', 'y', 'z'}
)

var NUM_LETTERS_COR_MAP = map[uint8][]uint8{
	CHAR_ONE:   ONE_LETTERS,
	CHAR_TWO:   TWO_LETTERS,
	CHAR_THREE: THREE_LETTERS,
	CHAR_FOUR:  FOUR_LETTERS,
	CHAR_FIVE:  FIVE_LETTERS,
	CHAR_SIX:   SIX_LETTERS,
	CHAR_SEVEN: SEVEN_LETTERS,
	CHAR_EIGHT: EIGHT_LETTERS,
	CHAR_NINE:  NINE_LETTERS,
}

func recurve(digits string, ret *[]string, index int, cur []uint8) {
	if index == len(digits) {
		// 递归结束，加入结果集
		if len(cur) > 0 {
			*ret = append(*ret, string(cur))
		}
		return
	}

	digit := digits[index]
	letters := NUM_LETTERS_COR_MAP[digit]
	for _, letter := range letters {
		recurve(digits, ret, index+1, append(cur, letter))
	}
}

func LetterCombinations(digits string) []string {
	var ret []string
	recurve(digits, &ret, 0, []uint8{})
	return ret
}
