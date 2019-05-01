import random


class Syncopater():
    def __init__(self, args, abclines, key, length):
        self.args = args
        self.abc = abclines
        self.sigkey = key
        self.length = int(length[2:])
        self.length_choices = [float(x) + 1 for x in range(self.length)] + [0.25, 0.5]

    def syncopate(self):
        syncopated_music = []
        measure_count = 0
        for line in self.abc:
            if '%' in line:
                line = line[:line.find('%')]
            line = line.strip(' \n').strip('\n')
            measures = [x for x in line.split('|')]
            while '' in measures:
                measures.remove('')
            new_measures = []
            for measure in measures:
                notes_and_dur = [self.sigkey.get_note_duration(note) for note in self.sigkey.get_notes(measure)]
                if measure_count < self.args.beginning_measure or measure_count > self.args.end_measure:
                    new_measure = self.sigkey.rest + str(self.length)
                elif self.args.syncopation == 1:
                    new_measure = self.syncopate_1(notes_and_dur)
                elif self.args.syncopation == 2:
                    new_measure = self.syncopate_2(notes_and_dur)
                elif self.args.syncopation == 3:
                    new_measure = self.syncopate_3(notes_and_dur)
                elif self.args.syncopation == 4:
                    new_measure = self.syncopate_4(notes_and_dur)
                elif self.args.syncopation == 5:
                    new_measure = self.syncopate_5()
                else:
                    new_measure = ''
                measure_count += 1
                new_measures += [' ' + new_measure + ' ']
            newline = '|'.join(new_measures) + '|\n'
            syncopated_music += [newline]
        return syncopated_music

    def syncopate_1(self, notes_and_dur):
        new_measure = ''
        for note, duration in notes_and_dur:
            new_measure += self.sigkey.get_complement_note(note)
            if duration != '1':
                new_measure += duration
        return new_measure

    def syncopate_2(self, note_and_dur):
        new_measure = ''
        for note, duration in note_and_dur:
            duration_float = self.sigkey.get_duration_as_float(duration)
            if duration_float >= 2.0:
                for x in self.get_sync_half_note(note, duration_float):
                    new_measure += x
            else:
                new_measure += self.sigkey.get_complement_note(note)
                if duration_float != 1.0:
                    new_measure += duration
        return new_measure

    def syncopate_3(self, notes_and_dur):
        new_measure = ''
        for note, duration in notes_and_dur:
            duration_float = self.sigkey.get_duration_as_float(duration)
            if duration_float >= 4.0:
                for x in self.get_sync_quarter_note(note, duration_float):
                    new_measure += x
            elif duration_float >= 2.0:
                for x in self.get_sync_half_note(note, duration_float):
                    new_measure += x
            else:
                new_measure += self.sigkey.get_complement_note(note)
                if duration_float != 1.0:
                    new_measure += duration
        return new_measure

    def syncopate_4(self, notes_and_dur):
        new_measure = ''
        durations = self.get_note_durations(notes_and_dur)
        if len(durations) >= 2:
            replace_val = self.sigkey.get_duration_as_str(self.sigkey.get_duration_as_float(durations[0]) + self.sigkey.get_duration_as_float(durations[1]))
            del durations[0:2]
            durations.insert(0, replace_val)
        random.shuffle(durations)
        for duration in durations:
            new_measure += self.sigkey.get_complement_note('B')
            if duration != '1':
                new_measure += duration
        return new_measure

    def syncopate_5(self):
        new_measure = ''
        note_lengths = []
        while sum(note_lengths) < self.length:
            next_note = random.choice(self.length_choices)
            if sum(note_lengths) + next_note <= self.length:
                note_lengths += [next_note]
        note_lengths = [self.sigkey.get_duration_as_str(x) for x in note_lengths]
        for note in note_lengths:
            new_measure += self.sigkey.get_complement_note('B')
            if note != '1':
                new_measure += note
        return new_measure

    def get_sync_half_note(self, note, split_len):
        half1 = int(split_len // 2)
        half2 = int(split_len - half1)
        note1 = self.sigkey.get_complement_note(note)
        if half1 != 1:
            note1 += str(half1)
        note2 = self.sigkey.get_complement_note(note)
        if half2 != 1:
            note2 += str(half2)
        return note1, note2

    def get_sync_quarter_note(self, note, split_len):
        q1 = q2 = q3 = int(split_len // 4)
        q4 = int(split_len - 3*q1)
        note1 = self.sigkey.get_complement_note(note)
        if q1 != 1:
            note1 += str(q1)
        note2 = self.sigkey.get_complement_note(note)
        if q2 != 1:
            note2 += str(q2)
        note3 = self.sigkey.get_complement_note(note)
        if q3 != 1:
            note2 += str(q3)
        note4 = self.sigkey.get_complement_note(note)
        if q4 != 1:
            note2 += str(q4)
        return note1, note2, note3, note4

    @staticmethod
    def get_note_durations(notes_and_dur):
        durations = []
        for note, duration in notes_and_dur:
            durations += [duration]
        return durations
