# !/usr/bin/env python3

import sqlite3
from sqlite3 import Error
import sys
sys.path.append('/home/barestides/dev/ihy/webapp/lib/')
from pyabc import Tune, Note


def create_connection(db_file):
    try:
        conn = sqlite3.connect(db_file)
        return conn
    except Error as e:
        print(e)

    return None


def abc_for_score_id(db_conn, score_id):
    cur = db_conn.cursor()
    cur.execute("SELECT abc FROM SCORE WHERE id=?", (score_id,))
    rows = cur.fetchone()
    return rows[0]



abcString = """
 X:1
  T:Speed the Plough
  M:4/4
  C:Trad.
  K:G
  |:GABc dedB|dedB dedB|c2ec B2dB|c2A2 A2BA| GABc dedB|dedB dedB|c2ec B2dB|A2F2 G4:| |:g2gf gdBd|g2f2 e2d2|c2ec B2dB|c2A2 A2df| g2gf g2Bd|g2f2 e2d2|c2ec B2dB|A2F2 G4:|
"""

# someTune = Tune(abcString)
# tuneNotes = someTune.notes
# tokens = someTune.tokens


def analyze_abc(score_id):
    db_filepath = "/home/braden/dev/ihy/webapp/dev/dev-db.db"
    db_conn = create_connection(db_filepath)
    abc_string = abc_for_score_id(db_conn, score_id)
    tune = Tune(abc_string)
    print(tune.notes)

# print(someTune.tokens)

# for n in tuneNotes:
#     print(n.pitch, n.duration)
