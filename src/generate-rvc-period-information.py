#!/usr/bin/env python3

# Script for generating RVC period information for ARIB STD-T109.
# Copyright (C) 2016 Julian Heinovski <julian.heinovski@ccs-labs.org>

# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.

# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.

# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.


import argparse


# TODO generation
def generate_rvc_periods():
    """
    Generates Roadside-to-Vehicle period information details according to the cound of base stations in the system.
    """

# TODO argparse
def main():
    """
    """

    parser = argparse.ArgumentParser(description="Tools for ARIB STD-T109. Copyright (C) 2016 Julian Heinovski <mail@julian-heinovski.de>")
    parser.add_argument("periods", type=int, help="Generates road")
    parser.add_argument("--base-station-count")
    parser.add_argument("--minimum-length")
    parser.add_argument("--random")
    parser.add_argument("--fairness")

    args = parser.parse_args()


# entry point for interactive mode
if __name__ == "__main__":
    main()
