import random


class Complexifier():
    def __init__(self, args, abc_lines, key):
        self.args = args
        self.abc = abc_lines
        self.sigkey = key

    def complexify(self):
        analyzed_music = []
        scale = self.sigkey.get_full_scale(self.sigkey.pentatonic_scale)
        if self.args.complexity == 1:
            dist_vals = (0, 4)
        elif self.args.complexity == 2:
            dist_vals = (2, 10)
        elif self.args.complexity == 3:
            dist_vals = (4, 15)
        elif self.args.complexity == 4:
            dist_vals = (6, 17)
        elif self.args.complexity == 5:
            dist_vals = (8, 20)
        else:
            dist_vals = (0, 4)
        for line in self.abc:
            notes_and_dur = [self.sigkey.get_note_duration(note) for note in self.sigkey.get_notes(line)]
            notes = self.get_list_of_notes(notes_and_dur)
            for i in range(len(notes) - 1):
                dist = self.sigkey.get_distance_between_notes(notes[i], notes[i + 1], scale)
                while dist < dist_vals[0] or dist > dist_vals[1]:
                    notes[i + 1] = random.choice(scale)
                    dist = self.sigkey.get_distance_between_notes(notes[i], notes[i + 1], scale)
            new_measures = []
            new_note_index = 0
            measures = line.strip('\n').split('|')
            while '' in measures:
                measures.remove('')
            for measure in measures:
                new_measure = ''
                if self.sigkey.rest in measure:
                    new_measures += [measure]
                else:
                    orig_notes = self.sigkey.get_notes(measure)
                    for i in range(len(orig_notes)):
                        new_measure += notes[new_note_index]
                        if self.sigkey.get_note_duration(orig_notes[i])[1] != '1':
                            new_measure += self.sigkey.get_note_duration(orig_notes[i])[1]
                        new_note_index += 1
                    new_measures += [' ' + new_measure + ' ']
            newline = '|'.join(new_measures) + '|\n'
            analyzed_music += [newline]
        return analyzed_music

    @staticmethod
    def get_list_of_notes(notes_and_dur):
        notes = []
        for note, duration in notes_and_dur:
            notes += [note]
        return notes
