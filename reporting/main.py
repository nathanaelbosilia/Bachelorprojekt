import os
import sys

import numpy as np
import re


def read_data_files(file_path):
    data = {}

    with open(file_path, 'r', encoding='utf-8') as file:
        for line in file:
            parts = line.split(':')
            if len(parts) == 2:
                key = parts[0].strip()
                try:
                    value = int(parts[1].strip())
                    data[key] = value
                except ValueError:
                    pass  # ignore non-numeric values
    return data


def aggregate_data_files(directory):
    file_prefix = "data"
    all_data = []

    for filename in os.listdir(directory):
        if filename.startswith(file_prefix) and filename.endswith('.txt'):
            file_path = os.path.join(directory, filename)
            if os.path.isfile(file_path):
                data = read_data_files(file_path)
                all_data.append(data)

    if not all_data:
        print(f"No valid {file_prefix} files found.")
        return

    print(f"Aggregated statistics for {file_prefix} files:")

    keys = set()
    for data in all_data:
        for key in data.keys():
            keys.add(key)

    for key in sorted(keys):
        values = [data[key] for data in all_data if key in data]
        if values:
            mean_value = np.mean(values)
            min_value = np.min(values)
            max_value = np.max(values)
            print(
                f"{key}: mean = {int(mean_value)}, min = {int(min_value)}, max = {int(max_value)}")


def read_run_files(file_path):
    data = {}

    with open(file_path, 'r', encoding='utf-8') as file:
        for line in file:

            key = "http_reqs"
            if key in line:
                try:
                    match = re.search(r"(\d+)\s+([\d.]+)/s", line)
                    data[key] = int(match.group(1).strip())
                    data[key + "_per_second"] = float(match.group(2).strip())
                except ValueError:
                    pass

            key = "http_req_failed"
            if key in line:
                try:
                    match = re.search(r"(\d+\.\d+)%", line)
                    data[key] = float(match.group(1).strip())
                except ValueError:
                    pass

            key = "http_req_duration"
            if key in line:
                try:
                    time_pattern = re.compile(r'(avg|min|max)=(\d+\.?\d*)([a-zµ]+)')
                    for match in time_pattern.finditer(line):
                        key2, value, unit = match.groups()
                        data[key + "_" + key2] = convert_to_ms(value, unit)
                except ValueError:
                    pass

            key = "expected_response:true"
            if key in line:
                key_name = "http_req_duration_201"
                try:
                    time_pattern = re.compile(r'(avg|min|max)=(\d+\.?\d*)([a-zµ]+)')
                    for match in time_pattern.finditer(line):
                        key2, value, unit = match.groups()
                        data[key_name + "_" + key2] = convert_to_ms(value, unit)
                except ValueError:
                    pass

    return data


def aggregate_run_files(directory):
    file_prefix = "run"
    all_data = []

    for filename in os.listdir(directory):
        if filename.startswith(file_prefix) and filename.endswith('.txt'):
            file_path = os.path.join(directory, filename)
            if os.path.isfile(file_path):
                data = read_run_files(file_path)
                all_data.append(data)

    if not all_data:
        print(f"No valid {file_prefix} files found.")
        return

    print(f"Aggregated statistics for {file_prefix} files:")

    key = "http_reqs"
    if any(key in data for data in all_data):
        values = [data[key] for data in all_data if key in data]
        if values:
            mean_value = np.mean(values)
            min_value = np.min(values)
            max_value = np.max(values)
            print(
                f"{key}: mean = {int(mean_value)}, min = {int(min_value)}, max = {int(max_value)}")

    key = "http_reqs_per_second"
    if any(key in data for data in all_data):
        values = [data[key] for data in all_data if key in data]
        if values:
            mean_value = np.mean(values)
            min_value = np.min(values)
            max_value = np.max(values)
            print(
                f"{key}: mean = {mean_value:.2f}/s, min = {min_value:.2f}/s, max = {max_value:.2f}/s")

    key = "http_req_failed"
    if any(key in data for data in all_data):
        values = [data[key] for data in all_data if key in data]
        if values:
            mean_value = np.mean(values)
            min_value = np.min(values)
            max_value = np.max(values)
            print(
                f"{key}: mean = {mean_value:.2f}%, min = {min_value:.2f}%, max = {max_value:.2f}%")

    key = "http_req_duration_avg"
    if any(key in data for data in all_data):
        values = [data[key] for data in all_data if key in data]
        if values:
            mean_value = np.mean(values)
            min_value = np.min(values)
            max_value = np.max(values)
            print(
                f"{key}: mean = {mean_value:.2f}ms, min = {min_value:.2f}ms, max = {max_value:.2f}ms")

    key = "http_req_duration_201_avg"
    if any(key in data for data in all_data):
        values = [data[key] for data in all_data if key in data]
        if values:
            mean_value = np.mean(values)
            min_value = np.min(values)
            max_value = np.max(values)
            print(
                f"{key}: mean = {mean_value:.2f}ms, min = {min_value:.2f}ms, max = {max_value:.2f}ms")

    key = "http_req_duration_201_min"
    if any(key in data for data in all_data):
        values = [data[key] for data in all_data if key in data]
        if values:
            mean_value = np.mean(values)
            min_value = np.min(values)
            max_value = np.max(values)
            print(
                f"{key}: mean = {mean_value:.2f}ms, min = {min_value:.2f}ms, max = {max_value:.2f}ms")

    key = "http_req_duration_min"
    if any(key in data for data in all_data):
        values = [data[key] for data in all_data if key in data]
        if values:
            mean_value = np.mean(values)
            min_value = np.min(values)
            max_value = np.max(values)
            print(
                f"{key}: mean = {mean_value:.2f}ms, min = {min_value:.2f}ms, max = {max_value:.2f}ms")

    key = "http_req_duration_max"
    if any(key in data for data in all_data):
        values = [data[key] for data in all_data if key in data]
        if values:
            mean_value = np.mean(values)
            min_value = np.min(values)
            max_value = np.max(values)
            print(
                f"{key}: mean = {mean_value:.2f}ms, min = {min_value:.2f}ms, max = {max_value:.2f}ms")

    key = "http_req_duration_201_max"
    if any(key in data for data in all_data):
        values = [data[key] for data in all_data if key in data]
        if values:
            mean_value = np.mean(values)
            min_value = np.min(values)
            max_value = np.max(values)
            print(
                f"{key}: mean = {mean_value:.2f}ms, min = {min_value:.2f}ms, max = {max_value:.2f}ms")


def convert_to_ms(value, unit):
    if unit == 'ms':
        return float(value)
    elif unit == 'µs':
        return float(value) / 1000
    elif unit == 's':
        return float(value) * 1000
    elif 'm' in unit:
        return float(value) * 60 * 1000
    elif 'm' in unit and 's' in unit:
        minutes, seconds = map(float, unit.split('m'))
        return (minutes * 60) + (seconds * 1000)
    else:
        raise ValueError(f"Unknown unit: {unit}")


def main():
    current_directory = os.getcwd()
    experiment_directories = []
    experiment_directories_name_pattern = re.compile(r"^experiment\d+$")
    for directory_name in os.listdir(current_directory):
        directory_path = os.path.join(current_directory, directory_name)
        if os.path.isdir(directory_path) and experiment_directories_name_pattern.match(directory_name):
            experiment_directories.append(directory_path)

    for experiment_directory in experiment_directories:
        for subdirectory in ["monolith", "microservices"]:
            directory = os.path.join(experiment_directory, subdirectory)
            if os.path.isdir(directory):
                report_path = os.path.join(directory, "report.txt")
                with open(report_path, "w") as report_file:
                    sys.stdout = report_file
                    print(f"Processing: {"/".join(directory.split(os.sep)[-2:])}")
                    aggregate_data_files(directory)
                    print("\n")
                    aggregate_run_files(directory)
                sys.stdout = sys.__stdout__


if __name__ == "__main__":
    main()
