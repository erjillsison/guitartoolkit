import pprint
notes = ["C","C#","D","D#","E","F","F#","G","G#","A","A#","B"]

major = (1,3,5,6,8,10,12)
minor = (1,3,4,6,8,9,11)

scales={"major":major,"minor":minor}
currScales={}
def convertToNotes(l):
    g=[]
    x=0
    for n in l:
        g.append(notes[n-1])
        x+=1
    return g

def getNotes(y):    
    for key, value in scales.items():
        rootMajor =[]
        for x in value:
            rootMajor.append((x + y) if (x + y)<13 else (x + y - 12))
        currScales["%s %s"%(notes[rootMajor[0]-1],key)] = rootMajor
        
        
def populateNoteList():
    for i in range(12):
        getNotes(i)
    pprint.pprint(currScales)

def areNotesInScale():
    for key, value in currScales.items():
        if(set(d).issubset(set(value))):
            print(key)
            print(convertToNotes(value))

            
def convertToIndex(d):
    x=0
    for n in d:
        d[x]=notes.index(str(n))+1
        x+=1
           
d=[]
x=0
while(True):
    y = input("Enter a note or y to finish ")
    y=y.upper()
    if(x>=3 and y=="Y"):
        print(d)
        convertToIndex(d)
        populateNoteList()
        areNotesInScale()
    elif (y not in d and y in notes):
        d.append(y)
    else:
        print("Invalid Input")
        continue
    x+=1
    

populateNoteList()
