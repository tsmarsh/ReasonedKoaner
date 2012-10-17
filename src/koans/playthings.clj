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
  	(== true q))
    __)

  "1.13 So what is the value here?"
  (= (run* [q]
  	s#
  	(== true q))
    __)

  "1.15 You can associate any value."
  (= (run* [r]
  	s#
  	(== 'corn r))
    __)

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

  "1.34 What happens when only one association succeeds?"
  (= (run* [q]
       (== false q)
       (== true q))
    __)

  "1.35 What is the value of q?"
  (= (run* [q]
       (== false q)
       (== false q))
    __)

  "1.36 Let makes x the same as q"
  (= (run* [q]
       (let [x q]
         (== true x)))
    __)

  "1.37 but fresh leaves x and r co-refering"
  (= (run* [r]
       (fresh [x]
         (== true x)))
    __)

  "1.38 but once an associate is made a value can be taken"
  (= (run* [q]
       (fresh [x]
         (== true x)
         (== q x)))
    __)

  "1.39 that association can happen at any point"
  (= (run* [q]
       (fresh [x]
         (== q x)
         (== true x)))
    __)

  "1.41 Basic clojure cond"
  (= (cond false true :else false) __)

  "1.43 What happens when we replace with succeed and fail?"
  (= (cond false s# :else u#) __)

  "1.44 Conde is logic equivalent of cond, but it doesn't have an else clause. The e stands for every line"
  (= (run* (conde
       (u# s#)))
    __)

  "1.47 What is the value of x?"
  (= (run* [x]
       (conde
         ((== :olive x) s#)
         ((== :oil x) s#)))
    __)

  "1.49 What is the value of x?"
  (= (run 1 [x]
       (conde
         ((== :olive x) s#)
         ((== :oil x) s#)))
    __)

  "1.50 Failing conditions are ignored"
  (= (run* [x]
       (conde
         ((== :virgin x) u#)
         ((== :olive x) s#)
         (s# s#)
         ((== :oil x) s#)))
    __)

  "1.52 It is easy to limit the results"
  (= (run 2 [x]
       (conde
         ((== :extra x) s#)
         ((== :virgin x) u#)
         ((== :olive x) s#)
         (s# s#)
         ((== :oil x) s#)))
    __)

  "1.53 What is the value of r"
  (= (run* [r]
       (fresh [x y]
         (== :split x)
         (== :pea y)
         (== (cons x (cons y ())) r )))
    __ )

  "1.54 What is the value of r"
  (= (run* [r]
       (fresh [x y]
        (conde
          ((== :split x) (== :pea y))
          ((== :navy x) (== :bean y)))
          (== (cons x (cons y (cons :soup ()))) r )))
    __ )

  (defn teacupo [x]
      (conde
        ((== :tea x) s#)
        ((== :cup x) s#)))

  "1.56 You can use functions to break the code up"
  (= (run* [x] (teacupo x)) __)

  "1.57 Which makes the value of r"
  (= (run* [r]
       (fresh [x y]
         (conde
           ((teacupo x) (== true y) s#)
           ((== false x) (== true y)))
         (== (cons x (cons y ())) r)))

    __)

  "1.58 and when the variables are fresh we get"
  (= (run* [r]
       (fresh [x y z]
         (conde
           ((== y x)(fresh [x]
                      (== z x)))
           ((fresh [x] (== y x)) (== z x)))
         (== (cons y (cons z())) r)))
    __)

  "1.59 which looks like _.0 and _.1 come from the same variables, but they do not"
  (= (run* [r]
       (fresh [x y z]
         (conde
           ((== y x)(fresh [x]
                      (== z x)))
           ((fresh [x] (== y x)) (== z x)))
         (== false x)
         (== (cons y (cons z())) r)))
    __)

  "1.60 Remember that the value of an assignment is a goal"
  (= (run* [q]
       (let [a (== true q) b (== false q)] b))
    __)

  "1.61 and that the value of fresh and conde are also goals"

  (= (run* [q]
        (let [a (== true  q)
              b (fresh [x]
                  (== x q)
                  (== false x))
              c (conde
                  ((== true q) s#))] b))
    __ )
)
