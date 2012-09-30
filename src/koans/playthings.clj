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
)
