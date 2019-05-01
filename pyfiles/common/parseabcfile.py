# !/usr/bin/env python3

from key import Key
import cliparser
from syncopater import Syncopater
from complexifier import Complexifier


class ParseABCFile:
    def __init__(self, args=None):
        self.args = cliparser.get_parser().parse_args(args)
        self.sigkey = None
        self.voices = []
        self.meter = None
        self.synthesized_music = ''
        self.is_prepended = True
        self.length = '1/4'
        self.doit()

    def doit(self):
        if self.args.end_measure == -1:
            self.args.end_measure = 100000000000
        file_lines = self.read_file()
        header = []
        body = []
        for line in file_lines:
            if line[0] == '<' or line[1] == ':' or line[0] == '%':
                header += [line]
            else:
                body = [file_lines[x] for x in range(len(file_lines)) if file_lines[x] not in header]
                break
        self.synthesized_music += ''.join(self.parse_header(header))
        self.synthesized_music += self.parse_body(body)
        print(self.synthesized_music)
        self.write_file(self.synthesized_music)

    def parse_header(self, header_lines):
        for line in header_lines:
            value = line.strip('\n').split(':')
            if value[0] == 'K':
                # self.sigkey = Key(value[1])
                self.sigkey = Key('C')
                self.sigkey.calculate_scales()
            elif value[0] == 'M':
                if 'C' in value[1]:
                    if '|' in line[1]:
                        self.meter = [2, 2]
                    else:
                        self.meter = [4, 4]
                else:
                    a, b = value[1].split('/')
                    self.meter = [int(a), int(b)]
            elif value[0] == 'V':
                voice = value[1].split()[0]
                if voice not in self.voices:
                    self.voices += [voice]
            elif value[0] == 'L':
                self.length = value[1].split()[0]

        if self.voices == []:
            self.voices += ['V1']
            header_lines.insert(-1, 'V:V1 treble\n')
        self.voices += ['V2']
        header_lines += ['V:V2 treble\n']

        return header_lines

    def parse_body(self, body_lines):
        for line in body_lines:
            if '[' not in body_lines:
                self.is_prepended = False
            if line[0] == '%' or line[0:2].upper() == 'W:':
                body_lines.remove(line)
        syncopated_lines = Syncopater(self.args, body_lines, self.sigkey, self.length).syncopate()
        synthesized_lines = Complexifier(self.args, syncopated_lines, self.sigkey).complexify()
        assert len(synthesized_lines) == len(body_lines), 'Length does not match'

        synthesized_abc = ''
        for i in range(len(body_lines)):
            if not self.is_prepended:
                synthesized_abc += '[V:' + self.voices[0] + '] '
            synthesized_abc += body_lines[i]
            synthesized_abc += '[V:' + self.voices[-1] + '] ' + synthesized_lines[i]
        return synthesized_abc

    def read_file(self):
        file = open(self.args.origin_path, 'r')
        lines = file.readlines()
        file.close()
        return lines

    def write_file(self, message):
        file = open(self.args.new_path, 'w')
        file.write(message)
        file.close()


if __name__ == '__main__':
    ParseABCFile()
