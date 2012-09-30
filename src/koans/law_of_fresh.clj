(meditations
	"If x is fresh, the (v x) succeeds and associates x with v"
	(= (run* [q] 
		(fresh [x] 
			(==  x true)
			(== true q)
			))
		__)
)