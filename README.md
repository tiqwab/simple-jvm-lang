### TODO

- array type
- function
- (class or struct)

### Function

- Use `MethodVisitor` in asm library
- At first, enable to call function
- Second, handle scopes correctly

### PROBLEM

```
int x = 5
if (x < 6) x = 7 // error since the type of if-statement is not evaluated before.
else 0
x
```

```
int x = 5
if (true) {
    int y = 6
    x = x + y
} else {
    int y = 7
    x = x + y
}
```

### NOT TODO

- error handling
