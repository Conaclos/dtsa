note
	description: "Usual network constants."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	NETWORK_CONSTANT

feature -- Access

	Loopback_ipv4: STRING_8 = "127.0.0.1"
			-- Looback IPv4.

	Loopback_ipv6: STRING_8 = "::1/128"
			-- Loopback IPv6 with eero suppressed format.

	Large_loopback_ipv6: STRING_8 = "0:0:0:0:0:0:0:1/128"
			-- Loopback IPv6 with large format.

end
