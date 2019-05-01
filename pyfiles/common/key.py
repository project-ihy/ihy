import random
import re


class Key:
    def __init__(self, key):
        self.key = key.replace('#', '^').replace('b', '_')
        self.notes = ('C', ('^C', '_D'), 'D', ('^D', '_E'), 'E', 'F', ('^F', '_G'), 'G', ('^G', '_A'), 'A', ('^A', '_B'), 'B')
        self.rest = 'z'
        self.major_scale = None
        self.pentatonic_scale = None
        self.accidentals = None
        self.OCTAVE1 = 'OCTAVE1'
        self.OCTAVE2 = 'OCTAVE2'
        self.OCTAVE3 = 'OCTAVE3'
        self.OCTAVE4 = 'OCTAVE4'

    def calculate_offset(self):
        for x in range(len(self.notes)):
            if len(self.key) == 2:
                self.key = self.key[1] + self.key[0]
            if self.notes[x] == self.key or self.key in self.notes[x]:
                return x
        return None

    def calculate_scales(self):
        offset = self.calculate_offset()
        if offset is None:
            print('Invalid Key')
            exit(0)
        scale = self.notes[offset:] + self.notes[:offset]
        self.major_scale = [scale[x] for x in [0, 2, 4, 5, 7, 9, 11]]
        self.major_scale = self.remove_nested_notes(self.major_scale)
        self.pentatonic_scale = [scale[x] for x in [0, 2, 4, 7, 9]]
        self.pentatonic_scale = self.remove_nested_notes(self.pentatonic_scale)
        self.accidentals = [scale[x] for x in [1, 3, 6, 8, 10]]
        self.accidentals = self.remove_nested_notes(self.accidentals)

    @staticmethod
    def remove_nested_notes(scale):
        linear_scale = []
        for x in scale:
            if type(x) is tuple:
                linear_scale += [i for i in x]
            else:
                linear_scale += [x]
        return linear_scale

    @staticmethod
    def get_notes(music_string):
        pattern = re.compile(r'[_^]*[a-gA-G][,\']*[/2-9]*')
        return pattern.findall(music_string)

    @staticmethod
    def get_note_duration(note_and_len):
        note = re.compile(r'[_^]*[a-gA-G][,\']*').search(note_and_len).group(0)
        if len(note_and_len) == len(note):
            return note, '1'
        else:
            return note, note_and_len[len(note):]

    @staticmethod
    def get_duration_as_float(duration):
        if duration[0] == '/':
            return 1/int(duration[1:])
        return float(duration)

    @staticmethod
    def get_duration_as_str(duration):
        return '/' + str(duration.as_integer_ratio()[1]) if duration.as_integer_ratio()[1] > 1 else str(int(duration))

    @staticmethod
    def get_distance_between_notes(note1, note2, full_scale):
        i1, i2 = full_scale.index(note1), full_scale.index(note2)
        if i1 == -1 or i2 == -1:
            return None
        return abs(i1 - i2)

    def is_note(self, note):
        s1, s2, s3, s4 = self.get_octaves(self.notes)
        return note in s1 or note in s2 or note in s3 or note in s4

    def is_note_in_scale(self, note, scale):
        s1, s2, s3, s4 = self.get_octaves(scale)
        return note in s1 or note in s2 or note in s3 or note in s4

    def get_octaves(self, scale):
        scale = self.remove_nested_notes(scale)
        scale1 = [x.lower() for x in scale]
        scale2 = [x.lower() + '\'' for x in scale]
        scale3 = [x + ',' for x in scale]

        # Return list of octaves from lowest to highest
        return scale3, scale, scale1, scale2

    def get_full_scale(self, scale):
        s1, s2, s3, s4 = self.get_octaves(scale)
        return s1 + s2 + s3 + s4

    def get_octave_from_note(self, note):
        if len(note) == 2 and note.isupper():
            return self.OCTAVE1
        elif len(note) == 1 and note.isupper():
            return self.OCTAVE2
        elif len(note) == 1 and note.islower():
            return self.OCTAVE3
        elif len(note) == 2 and note.islower():
            return self.OCTAVE4
        else:
            return None

    def get_random_lower_octave_pentatonic(self, note):
        octave = self.get_octave_from_note(note)
        s1, s2, s3, s4 = self.get_octaves(self.pentatonic_scale)

        if octave is None:
            return None
        elif octave == self.OCTAVE4:
            return random.choice(s3)
        elif octave == self.OCTAVE3:
            return random.choice(s2)
        elif octave == self.OCTAVE2:
            return random.choice(s1)
        else:
            return random.choice(s1)

    def get_accidental_harmonic_note(self, note):
        octave = self.get_octave_from_note(note)
        s1, s2, s3, s4 = self.get_octaves(self.accidentals)

        if octave is None:
            return None
        elif octave == self.OCTAVE4:
            return random.choice(s3)
        elif octave == self.OCTAVE3:
            return random.choice(s2)
        elif octave == self.OCTAVE2:
            return random.choice(s1)
        else:
            return random.choice(s1)

    def get_complement_note(self, note):
        if self.is_note_in_scale(note, self.major_scale):
            return self.get_random_lower_octave_pentatonic(note)
        elif self.is_note_in_scale(note, self.accidentals):
            return self.get_accidental_harmonic_note(note)
        return None
