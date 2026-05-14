import numpy as np

class Machine:
    '''
    transtion dictionary must be hashed using tuples (ie, states are tuples, and inputs are also tuples), and must return a triple, which 
    may also contain tuples. Let t be the transition function of M, q be a state, and a be an input, then 
    t(q, a) = (p, b, D)
    where D is a direction, b is the symbol to be replaced on the tape, and p is the new state.
    
    '''
    def __init__(self, states: list, alphabet: list, tape_alphabet: list, directions: list, transition_function: dict, initial_state: tuple, halting_state: tuple):
        self.states = states
        self.alphabet = alphabet
        self.tape_alphabet = tape_alphabet
        self.directions = directions
        self.transition_function = transition_function
        self.initial_state = initial_state
        self.halting_state = halting_state

    def simulate(self, inputlst: list[tuple]):
        '''
        '''
        current_state = self.initial_state
        halting_state = self.halting_state
        n = 0
        Machine.print_ID(inputlst, n)
        while current_state != halting_state:
            if n == len(inputlst) or n == -1:
                inputlst = self.addblank(inputlst, n)
            transition = self.transition_function[current_state][inputlst[n]]
            if transition == -1:
                print('error')
                break
            next_state, write, move = transition
            inputlst[n] = write
            if move == 'R':
                n +=1
            else:
                n -=1
            Machine.print_ID(inputlst, n)
            current_state = next_state
        if current_state == halting_state:
            print('accepted')
    
    def addblank(self, inputlst, n):
        '''
        '''
        new_list = []
        if n == -1:
            new_list.append(('B',))
            for sym in inputlst:
                new_list.append(sym)
                return new_list
        else:
            for sym in inputlst:
                new_list.append(sym)
            new_list.append(('B',))
            return new_list

    def print_ID(inputlst: list[tuple], n: int):
        '''
        '''
        temp = [tup for i, tup in enumerate(inputlst) if i < n]
        temp.append('q')
        i = n
        while i < len(inputlst):
            temp.append(inputlst[i])
            i+=1
        print(temp)

a = 'a'
b = 'b'
B = 'B'
c = 'c'
X = 'X'
R = 'R'
L = 'L'
q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11 = 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11

transition_anbncn = {
        (q1,B,B,B):{(X, a):-1,(B, a):((q2,a,B,B),(X,a), R),(X, b):-1,(B, b):-1,(X, c):-1,(B, c):-1,(B, B):-1},
        (q2,a,B,B):{(X, a):-1,(B, a):((q2,a,B,B),(B,a), R),(X, b):((q3,a,B,B),(X,b), R),(B, b):((q4,a,b,B),(X,b), R),(X, c):-1,(B, c):-1,(B, B):-1},
        (q3,a,B,B):{(X, a):-1,(B, a):-1,(X, b):((q3,a,B,B),(X,b), R),(B, b):((q4,a,b,B),(X,b), R),(X, c):-1,(B, c):-1,(B, B):-1},
        (q10,B,B,B):{(X, a):((q8,B,B,B),(X,a), R),(B, a):-1,(X, b):((q10,B,B,B),(X,b), L),(B, b):-1,(X, c):-1,(B, c):-1,(B, B):-1},
        (q4,a,b,B):{(X, a):-1,(B, a):-1,(X, b):-1,(B, b):((q4,a,b,B),(B,b), R),(X, c):((q5,a,b,B),(X,c), R),(B, c):((q6,a,b,c),(X,c), L),(B, B):-1},
        (q5,a,b,B):{(X, a):-1,(B, a):-1,(X, b):-1,(B, b):-1,(X, c):((q5,a,b,B),(X,c), R),(B, c):((q6,a,b,c),(X,c), L),(B, B):-1},
        (q11,B,B,B):{(X, a):((q8,B,B,B),(X,a), R),(B, a):-1,(X, b):((q11,B,B,B),(X,b), L),(B, b):-1,(X, c):((q11,B,B,B),(X,c), L),(B, c):-1,(B, B):-1},
        (q6,a,b,c):{(X, a):((q7,B,B,B),(X,a), R),(B, a):((q6,a,b,c),(B,a), L),(X, b):((q6,a,b,c),(X,b), L),(B, b):((q6,a,b,c),(B,b), L),(X, c):((q6,a,b,c),(X,c), L),(B, c):-1,(B, B):-1},
        (q7,B,B,B):{(X, a):-1,(B, a):((q2,a,B,B),(X,a), R),(X, b):((q8,B,B,B),(X,b), R),(B, b):-1,(X, c):-1,(B, c):-1,(B, B):-1},
        (q8,B,B,B):{(X, a):-1,(B, a):-1,(X, b):((q8,B,B,B),(X,b), R),(B, b):-1,(X, c):((q8,B,B,B),(X,c), R),(B, c):-1,(B, B):((q9,B,B,B),(B,B), R)},
        (q9,B,B,B):{(X, a):-1,(B, a):-1,(X, b):-1,(B, b):-1,(X, c):-1,(B, c):-1,(B, B):-1}
        }

anbncn = Machine(list(np.arange(1,12)), [a,b,c], [B, X, a, b, c], [L, R], transition_anbncn, (q1,B,B,B), (q9,B,B,B))
string = [('B','a' ), ('B', 'c'), ('B','c' ), ('B', 'c'), ('B','c')]

transition_multiplication = {
    (q1,):{(0, ):((q2,), (B,), R), (1, ):((q8,), (B,), R), (B, ):-1, (X, ):-1},
    (q2,):{(0, ):((q2,), (0,), R), (1, ):((q3,), (1,), R), (B, ):-1, (X, ):-1},
    (q3,):{(0, ):((q4,), (X,), R), (1, ):((q9,), (B,), R), (B, ):-1, (X, ):-1},
    (q4,):{(0, ):((q4,), (0,), R), (1, ):((q4,), (1,), R), (B, ):((q5,), (0,), L), (X, ):-1},
    (q5,):{(0, ):((q5,), (0,), L), (1, ):((q5,), (1,), L), (B, ):-1, (X, ):((q6,), (0,), R)},
    (q6,):{(0, ):((q4,), (X,), R), (1, ):((q7,), (1,), L), (B, ):-1, (X, ):-1},
    (q7,):{(0, ):((q7,), (0,), L), (1, ):((q7,), (1,), L), (B, ):((q1,), (B,), R), (X, ):-1},
    (q8,):{(0, ):((q8,), (B,), R), (1, ):((q9,), (B,), R), (B, ):-1, (X, ):-1},
    (q9,):{(0, ):-1, (1, ):-1, (B, ):-1, (X, ):-1}
}

multiplication = Machine(list(np.arange(1,10)), [0,1],[B,X,0,1], [L,R], transition_multiplication, (q1,), (q9,))
string = [(0,),(1,),(1,)]
multiplication.simulate(string)
