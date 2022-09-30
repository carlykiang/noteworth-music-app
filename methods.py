from flask import Flask, jsonify from flask import request import pymysql
def is_email_in_db(email):
    db = pymysql.connect(host="47.100.220.252", user="admin", password="abc123!X",
    database="test001") cursor = db.cursor()
    cursor.execute("SELECT * FROM test001.auth WHERE email='{0}'".format(email))

    data = cursor.fetchall()
    cursor.close() db.close()
if data==(): 
    return False
return True

def check_email_password_in_db(email, password):
    db = pymysql.connect(host="47.100.220.252", user="admin", password="abc123!X",
    database="music_app") cursor = db.cursor()
    cursor.execute("SELECT type,user_id FROM music_app.user_info WHERE email='{0}' AND password='{1}'".format(email,password))
    data = cursor.fetchall()
    cursor.close() db.close()

    if data==(): 
        return False
    return data[0][0]+'___'+str(data[0][1])

def insert_data(email,password,utype,grade,name):
    db = pymysql.connect(host="47.100.220.252", user="admin", password="abc123!X",
    database="music_app")
    cursor = db.cursor()
    sql = "INSERT INTO music_app.user_info (email,password,type,grade,name) VALUES
    ('{0}', '{1}', '{2}', {3}, '{4}')"
    sql = sql.format(email,password,utype,grade,name) cursor.execute(sql)
    db.commit()
    db.close()

def teacher_insert_assignment(teacher_id, student_id,pieces,scales,exercises,practice_days, status='incomplete'):
    db = pymysql.connect(host="47.100.220.252", user="admin", password="abc123!X", database="music_app")
    cursor = db.cursor()
    sql = "INSERT INTO music_app.homework (date, teacher_id, student_id,pieces,scales,exercises,practice_days, status) VALUES (NOW(), {0}, {1}, '{2}', '{3}', '{4}', '{5}', '{6}')".format(teacher_id, student_id,pieces,scales,exercises,practice_days, status)
    cursor.execute(sql) db.commit() db.close()

def get_all_student_info():
    db = pymysql.connect(host="47.100.220.252", user="admin", password="abc123!X",
    database="music_app") 
    cursor = db.cursor()
    cursor.execute("SELECT name,user_id FROM music_app.user_info WHERE type='student'") 
    data = cursor.fetchall()
    if data==():
        return 'none' ss = ''
    for s in data: 
        if ss=='':
            ss = s[0]+'___'+str(s[1]) 
        else:
            ss = ss + '%%%' + s[0]+'___'+str(s[1])
        
        cursor.close() 
        db.close() 
        return ss

def get_all_student_info():
    db = pymysql.connect(host="47.100.220.252", user="admin", password="abc123!X",
    database="music_app") cursor = db.cursor()
    cursor.execute("SELECT name,user_id FROM music_app.user_info WHERE type='student'") data = cursor.fetchall()
   
    if data==():
        return 'none' ss = ''
    for s in data: 
        if ss=='':
    ss = s[0]+'___'+str(s[1]) 
    else:
        ss = ss + '%%%' + s[0]+'___'+str(s[1])
    cursor.close() 
    db.close()
    return ss

app = Flask(__name__)

# http://47.100.220.252:666/auth/?email=aaa@bbb.com&password=123 @app.route('/auth/')
def auth():
    email = request.args.get("email")
    password = request.args.get("password")
    r = check_email_password_in_db(email,password) 
    if r==False:
        return "false" 
    return r

# http://47.100.220.252:666/register/?email=a@bbb.com&password=123&type=teacher&grade=1 0&name="John"
@app.route('/register/')
def register():
    try:
        email = request.args.get("email")
        password = request.args.get("password") 
        utype = request.args.get("type")
        grade = request.args.get("grade")
        name = request.args.get("name") 
        insert_data(email,password,utype,grade,name) # duplicated email, .....
        return 'true'
    except Exception as e: 
        eturn 'false'

@app.route('/get_all_student/') 
def get_stu_info():
    try:
        result = 'none'
        user_id = request.args.get("user_id") 
        result = get_all_student_info() 
        return result
    except Exception as e: 
        return 'none'

@app.route('/auth_test/') 
def auth_test():
    user = request.args.get("user") 
    r="no"
    if user == "John":
        r= "yes" 
    return r

@app.route('/assignment_status_test/') 
def assignment_status_test():
    user = request.args.get("user") 
    r="incomplete"
    if user=="Jack":
        r="complete" 
    return r

@app.route('/get_overndigits_pass_users/') 
def get_overndigits_pass_users():
    # step-01: get parameters
    n = request.args.get("n")
    n = int(n)
    # step-02: connect database
    db = pymysql.connect(host="47.100.220.252", user="admin", password="abc123!X",
    database="music_app")
    cursor=db.cursor()
    #step-03: SQL
    sql='SELECT password FROM music_app.user_info' #step-04: query and process logic
    cursor.execute(sql) data = cursor.fetchall() num_long_pass =0
    for d in data:
        if len(d[0])>n: 
            num_long_pass+=1
        #step-05:
    cursor.close()
    db.close()
    return str(num_long_pass)


@app.route('/get_type_count_test/') 
def get_type_count_test():
    usertype = request.args.get("usertype")
    # step-01: connect database
    db = pymysql.connect(host="47.100.220.252", user="admin", password="abc123!X", database="music_app")
    cursor=db.cursor()
    #step 02
    sql='SELECT type FROM music_app.user_info' #step3
    cursor.execute(sql)
    data = cursor.fetchall()
    final_num = 0
    student_user_num=0
    teacher_user_num=0
    for d in data:
        if d[0]=='student': 
            student_user_num+=1
        if d[0] =='teacher': 
            teacher_user_num+=1
    if usertype == 'student': 
        final_num = student_user_num
    elif usertype == 'teacher': 
        final_num = teacher_user_num
    else:
        final_num = -1
    #step4
    cursor.close() db.close()
    return str(final_num)


# http://47.100.220.252:666/get_type_count_test/?usertype=student
def get_user_id_by_name(user_name):
# step-01: connect database
    db = pymysql.connect(host="47.100.220.252", user="admin", password="abc123!X",
    database="music_app")
    cursor = db.cursor()
    # step-02: construct sql, then execute the sql
    sql = 'SELECT user_id,name FROM music_app.user_info' # step-03: run the sql, get the query data cursor.execute(sql)
    data = cursor.fetchall()
    user_id = -1
    for d in data:
        user_id = d[0]
        name = d[1]
        if name==user_name:
            print(name,user_name) 
            break
    # step-04: close database cursor.close()
    db.close()
    return user_id


@app.route('/get_user_id_by_user_name/') 
def get_user_id_by_user_name():
    username = request.args.get("username") 
    user_id = get_user_id_by_name(username) 
    return str(user_id)


@app.route('/get_assignment_by_name/') 
def get_assignment_by_name():
    # step-01: get parameters
    username = request.args.get("username")
    # step-02: connect database
    db = pymysql.connect(host="47.100.220.252", user="admin", password="abc123!X",
    database="music_app")
    cursor=db.cursor()
    #step-03: SQL
    sid = get_user_id_by_name(username) print('get_user_id_by_name,',sid,username)
    sql='SELECT student_id, pieces, scales, exercises, practice_days FROM
    music_app.homework WHERE student_id={0}'.format(sid) print(sql)
    #step-04: query and process logic cursor.execute(sql)
    data = cursor.fetchall() assignments = str(data)
    print(assignments)
    #step-05:
    cursor.close()
    db.close()
    num_long_pass = assignments return str(num_long_pass)


@app.route('/get_assignment_status_by_name/') 
def get_assignment_status_by_name():
    # step-01: get parameters
    username = request.args.get("username")
    requested_status = request.args.get("status")
    # step-02: connect database
    db = pymysql.connect(host="47.100.220.252", user="admin", password="abc123!X",
    database="music_app")
    cursor=db.cursor()
    #step-03: SQL
    sid = get_user_id_by_name(username)
    print('get_user_id_by_name,',sid,username)
    sql='SELECT student_id, pieces, scales, exercises, status FROM music_app.homework
    WHERE student_id={0}'.format(sid) print(sql)
    #step-04: query and process logic cursor.execute(sql)
    data = cursor.fetchall() assignments = str(data) assignments_string = ""
    for d in data:
        status = d[4]
        pieces = d[1]
        scales = d[2]
        exercises = d[3]
        if (requested_status == status):
            assignments_string = assignments_string +" " + str(pieces) 
            assignments_string = assignments_string + " " + str(scales) 
            assignments_string = assignments_string + " " +str(exercises) 
            print(pieces)
            print(scales)
            print(exercises) 
    #step-05:
    cursor.close() db.close()
    return assignments_string


# http://47.100.220.252:666/teacher_submit_assignment/?teacher_id=111&student_id=222&piece s=aaa&scales=bbbexercises=ccc&practice_days=ddd @app.route('/teacher_submit_assignment/')
def teacher_submit_assignment():
    try:
        teacher_id = request.args.get("teacher_id")
        student_id = request.args.get("student_id")
        pieces = request.args.get("pieces")
        scales = request.args.get("scales")
        exercises = request.args.get("exercises")
        practice_days = request.args.get("practice_days") 
        teacher_insert_assignment(teacher_id, student_id,pieces,scales,exercises,practice_days) 
        return 'true'
    except Exception as e: 
        return 'false'

@app.route('/teacher_check_student_progress/') 
def teacher_check_student_progress():
    try:
    start_date = request.args.get("start_date")
    end_date = request.args.get("end_date")
    teacher_id = request.args.get("teacher_id")
    student_id = request.args.get("student_id")
    return get_assignments_by_date(teacher_id, student_id, start_date, end_date)
    except Exception as e: print(e)
    return 'false'


def get_assignments_by_date(teacher_id, student_id, start_date, end_date):
    db = pymysql.connect(host="47.100.220.252", user="admin", password="abc123!X",
    database="music_app") cursor = db.cursor()
    #select the data from the MySQL table
    cursor.execute("SELECT * FROM music_app.homework WHERE teacher_id={0} AND
    student_id={1} AND date>'{2}' AND date<'{3}'".format(teacher_id, student_id, start_date, end_date))
    data = cursor.fetchall() 
    if data==():
        return 'none'

    #collect the results of the MySQL search and turn into a string result = ''
    #for each row in the results from the MySQL table, 
    for row in data:
        line = ''
    #create a string for each row (line) 
        for col in row:
            if line=='': 
                line=str(col)
            else:
            #separate each column in the row with 2 underscores 
            line=line+'__'+str(col)
        if result=='': 
            result = line
        else:
        #separate each row with 4 underscores 
        result = result+'____'+line
    cursor.close() 
    db.close()
    return result


@app.route('/student_check_progress/') 
def student_check_progress():
    try:
        start_date = request.args.get("start_date")
        end_date = request.args.get("end_date")
        student_id = request.args.get("student_id")
        return get_all_student_assignments(student_id, start_date, end_date)
        except Exception as e: print(e)
        return 'false'

def get_all_student_assignments(student_id, start_date, end_date):
    db = pymysql.connect(host="47.100.220.252", user="admin", password="abc123!X",
    database="music_app") cursor = db.cursor()
    cursor.execute("SELECT * FROM music_app.homework WHERE student_id={0} AND date>'{1}' AND date<'{2}'".format(student_id, start_date, end_date))
    data = cursor.fetchall()
    if data==(): 
        return 'none'
    result = ''
    for row in data:
        line = ''
        for col in row:
            if line=='': 
                line=str(col)
            else: 
                line=line+'__'+str(col)
        if result=='': 
            result = line
        else:
            result = result+'____'+line
    
    cursor.close() 
    db.close() 
    return result

@app.route('/student_update_assignment_status/') 
def student_update_assignment_status():
    try:
        aid = request.args.get("aid") 
        status = request.args.get("status")
        return update_assignment_by_aid(aid,status) 
    except Exception as e:
        print(e) 
        return 'false'

def update_assignment_by_aid(aid,status):
    db = pymysql.connect(host="47.100.220.252", user="admin", password="abc123!X",
    database="music_app")
    cursor = db.cursor()
    sql = "UPDATE music_app.homework SET status='{0}' WHERE aid={1}".format(status,aid) cursor.execute(sql)
    db.commit() cursor.close() db.close() return 'true'


@app.route('/get_all_teacher_info_scoreboard/') 
def get_all_teacher_info_scoreboard():
    try:
        return get_all_teacher_info_for_score()
        except Exception as e: print(e)
        return 'false'

def get_user_name_by_id(uid):
    # step-01: connect database
    db = pymysql.connect(host="47.100.220.252", user="admin", password="abc123!X",
    database="music_app")
    cursor = db.cursor()
    # step-02: construct sql, then execute the sql
    sql = 'SELECT user_id,name FROM music_app.user_info'
    # step-03: run the sql, get the query data cursor.execute(sql)
    data = cursor.fetchall()
    user_name = ""
    for d in data: 
        user_id = d[0] 
        name = d[1]
        if uid==user_id:
            print(name, user_id) 
            user_name = name 
            break
    # step-04: close database 
    cursor.close()
    db.close()
    return user_name

def get_all_teacher_info_for_score():
    db = pymysql.connect(host="47.100.220.252", user="admin", password="abc123!X",
    database="music_app") 
    cursor = db.cursor()
    cursor.execute("SELECT teacher_id,student_id FROM music_app.homework") 
    data = cursor.fetchall()
    if data==(): 
        return 'none'

    id_to_assign_count = {} 
    for d in data:
        teacher_id = d[0]
        student_id = d[1]
        if teacher_id not in id_to_assign_count:
            id_to_assign_count[teacher_id] = 0 
        id_to_assign_count[teacher_id] += 1
    cursor.close() 
    db.close()

    result = ''
    for teacher_id in id_to_assign_count:
        print('tid={0},count={1}'.format(teacher_id,id_to_assign_count[teacher_id])) 
        teacher_name = get_user_name_by_id(teacher_id)
        result+= '{0}:{1}\n'.format(teacher_name, id_to_assign_count[teacher_id])
    return result



@app.route('/get_all_student_info_scoreboard/') 
def get_all_student_info_scoreboard():
    try:
        return get_all_student_info_for_score()
    except Exception as e: 
        print(e)
        return 'false'

def get_all_student_info_for_score():
    db = pymysql.connect(host="47.100.220.252", user="admin", password="abc123!X",
    database="music_app")
    cursor = db.cursor()
    cursor.execute("SELECT teacher_id,student_id FROM music_app.homework WHERE status = 'complete'")
    data = cursor.fetchall()

    if data==(): 
        return 'none'
    id_to_assign_count = {} 
    for d in data:
        teacher_id = d[0]
        student_id = d[1]
        if student_id not in id_to_assign_count:
            id_to_assign_count[student_id] = 0 
        id_to_assign_count[student_id] += 1
    cursor.close()
    db.close()
    result = ""
    for student_id in id_to_assign_count:
        student_name = get_user_name_by_id(student_id)
        result+= '{0}:{1}\n'.format(student_name,id_to_assign_count[student_id])
    return result

if __name__ == '__main__': 
    app.run(host="0.0.0.0", port='666',debug=True)