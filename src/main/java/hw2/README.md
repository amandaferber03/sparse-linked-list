# Discussion

The `Roster` class uses `IndexedList` to store a list of students. The
`Roster.find` implements the binary search algorithm. Which
implementation of the `IndexedList` should be used to implement the
`Roster` class? (It could be one or more of `ArrayIndexedList`,
`LinkedIndexList`, `SparseIndexedList`). And why?
   
--------------- Write your answers below this line ----------------

The ArrayIndexedList implementation would be most appropriate for the Roster class. Because the find() method uses 
a binary search algorithm, the list needs to be both sorted and easily accessible at the midpoint. If one were to use a
linked list, whether it was sparse or not, one would have to iterate through each node before arriving at the middle of
the list. It would be less expensive to use an array because, given the length of the list, one can simply divide the 
length by two and access the middle of the list by its index. In addition, each time the lower and upper bound of the list 
results in a smaller interval for which to search, you would still have to start at the head of the linked list. In addition, 
a sparse linked list would be poorly suited to represent a list of students because there is not necessarily a "default value." 
Each student is unique and should be accounted for.