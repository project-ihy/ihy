import argparse


def get_parser():
    parser = argparse.ArgumentParser()
    parser.add_argument("-b", "--beginning-measure",
                        dest="beginning_measure",
                        default=0,
                        help="Beginning measure of user selection",
                        type=int)
    parser.add_argument("-c", "--complexity",
                        dest="complexity",
                        default=1,
                        help="Complexity/smoothness on a scale of 1 to 5",
                        type=int)
    parser.add_argument("-e", "--end-measure",
                        dest="end_measure",
                        default=-1,
                        help="End measure of user selection",
                        type=int)
    parser.add_argument("-n", "--new-path",
                        dest="new_path",
                        help="Full path of the new abc file",
                        default="/",
                        type=str)
    parser.add_argument("-o", "--orig-path",
                        dest="origin_path",
                        help="Full path of the origin abc file",
                        default="/",
                        type=str)
    parser.add_argument("-s", "--syncopation",
                        help="Syncopation on a scale of 1 to 5",
                        dest="syncopation",
                        default=1,
                        type=int)
    return parser
