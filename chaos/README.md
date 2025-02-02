To activate the venv, run this command inside the ``chaos`` directory ``source venv/bin/activate``.
To check if the venv is activated, you can run ``which python``. It should point to the venv directory.
To deactivate it, simply run ``deactivate``.
To install the dependencies, run ``pip install -r requirements.txt``.
After that, you should be able to check the version of the installed chaos toolkit by running ``chaos --version``.

To activate an experiment, simply run ``chaos run experiments/request_assaults/latency_assault/monolith.json``.
You will have to adjust the path to the desired experiment accordingly.

To run the k6 load test on its own and not from within chaos toolkit, you would need to remove the chaosk6 
run_script action inside an experiment and run the experiment with the ``--rollback-strategy=never`` flag.
After that, you can start the k6 load test by running ``k6 run k6/path/to/test/test-name.ts``.
After the load test, you would need to run the experiment again but without the rollback flag to disable chaos monkey.
Unfortunately, I have not yet found a way of only running the rollback part of the experiment without the other actions.