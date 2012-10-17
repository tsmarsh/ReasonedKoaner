(meditations
  "1.10 run* always returns a list, even when it fails (u#)"
  (= (run* [q] u#) 
  	__)

  "1.11 A successful run can associate values to q"
  (= (run* [q] 
  	(== true q)) __)

  "1.12 Failing runs do not associate values."
  (= (run* [q]
  	u#
  	(== true q)) __)

  "1.13 So what is the value here?"
  (= (run* [q]
  	s#
  	(== true q)) __)

  "1.15 You can associate any value."
  (= (run* [r]
  	s#
  	(== 'corn r)) __)

  "1.22 You can assign variabels within a run."
  (= (run* [x] 
  		(let [x false] (== true x))) 
  	__)

  "1.23 You can create 'fresh' variables that have no associations."
  (= (run* [q]
  		(fresh [x] 
  			(== x true) 
  			(== true q))) 
  	__)

  "1.26 If x is fresh, the (v x) succeeds and associates x with v"
	(= (run* [q] 
		(fresh [x] 
			(==  x true)
			(== true q)
			))
		__)

	"1.28 _.0 is a variable representing a fresh variable."
	(= (run* [x] s#) __)

	"1.29 What is the value of x?"
	(= (run* [x] 
			(let [x false]
				(fresh [x] 
					(== true x))))
	__)

	"1.30 It is possible to return more than one fresh variable."
	(= (run* [r]
		(fresh [x y]
			(== (cons x (cons y '())) r)))
	__)

	"1.31 The name of the variable does not affect freshness."
	(= (run* [s]
		(fresh [t u]
			(== (cons t (cons u '())) s)))
	__)

	"1.32 The freshest fresh wins"
	(= (run* [r] 
		(fresh [x] 
			(let [y x]
				(fresh [x]
					(== (cons y (cons x (cons y '()))) r)))))
	__)

	"1.33 Freshness is applied in the order they are reified, not declared"
	(= (run* [r] 
		(fresh [x] 
			(let [y x]
				(fresh [x]
					(== (cons x (cons y (cons x '()))) r)))))
	__)
)
