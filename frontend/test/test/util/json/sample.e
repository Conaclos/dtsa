note
	description: "Summary description for {SAMPLE_MOCK}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	SAMPLE

create
	make

feature {NONE} -- Creation

	make (a_str: like str; a_int: like int)
		do
			str := a_str
			int := a_int
		ensure
			str_set: str = a_str
			int_set: int = a_int
		end

feature -- Access

	str: READABLE_STRING_GENERAL
			-- A string.

	int: INTEGER_8
			-- A integer.

end
