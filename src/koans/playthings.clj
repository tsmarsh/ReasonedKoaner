(meditations
  "run* always returns a list, even when it fails (u#)"
  (= (run* [q] u#) 
  	__)

  "A successful run can associate values to q"
  (= (run* [q] 
  	(== true q)) __)

  "Failing runs do not associate values."
  (= (run* [q]
  	u#
  	(== true q)) __)

  "So what is the value here?"
  (= (run* [q]
  	s#
  	(== true q)) __)

  "You can associate any value."
  (= (run* [r]
  	s#
  	(== 'corn r)) __)

  "You can assign variabels within a run."
  (= (run* [x] 
  		(let [x false] (== true x))) 
  	__)

  "You can create 'fresh' variables that have no associations."
  (= (run* [q]
  		(fresh [x] 
  			(== x true) 
  			(== true q))) 
  	__)

  	"If x is fresh, the (v x) succeeds and associates x with v"
	(= (run* [q] 
		(fresh [x] 
			(==  x true)
			(== true q)
			))
		__)

		"_.0 is a variable representing a fresh variable."
	(= (run* [x] s#) __)

	"What is the value of x?"
	(= (run* [x] 
			(let [x false]
				(fresh [x] 
					(== true x))))
	__)

	"It is possible to return more than one fresh variable."
	(= (run* [r]
		(fresh [x y]
			(== (cons x (cons y '())) r)))
	__)

	"The name of the variable does not affect freshness."
	(= (run* [s]
		(fresh [t u]
			(== (cons t (cons u '())) s)))
	__)

	"The freshest fresh wins"
	(= (run* [r] 
		(fresh [x] 
			(let [y x]
				(fresh [x]
					(== (cons y (cons x (cons y '()))) r)))))
	__)

	"Freshness is applied in the order they are reified, not declared"
	(= (run* [r] 
		(fresh [x] 
			(let [y x]
				(fresh [x]
					(== (cons x (cons y (cons x '()))) r)))))
	__)
)
