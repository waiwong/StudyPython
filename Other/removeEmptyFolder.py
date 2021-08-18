import os
import shutil
import argparse

# root = 'D:\Download\Pictures'
root = 'D:\Download\TEMP'
folders = list(os.walk(root))[1:]

for folder in folders:
    # print (folder)
    if (len(folder[1]) == 0 and len(folder[2]) == 0) or (len(folder[1]) == 0 and len(folder[2]) == 1 and (folder[2][0] == "desktop.ini" or folder[2][0].endswith(".torrent"))) :
        print (folder)
        #print(":: Delete folder: " + folder[0])
        ## os.rmdir(folder[0])
        ## shutil.rmtree(folder[0])
        print('rmdir /S /Q "{}"'.format(folder[0]))
        #os.system('rmdir /S /Q "{}"'.format(folder[0]))

    # if len(os.listdir(folder[0])) == 0:
    #     print (folder)
    #     print (len(os.listdir(folder[0])))
    # if len(os.listdir(folder[0])) == 1 or len(folder[2]) == 1:
    #     print (folder)
    #     print (len(os.listdir(folder[0])))
    # # folder example: ('FOLDER/3', [], ['file'])
    # if not folder[2]:
    #     print (folder[0])
    #     # 
    # 
    # C:\Users\waiwo\Anaconda3\python C:\MyGit\StudyPython\Other\removeEmptyFolder.py