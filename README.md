# CRISPR-X V1.2.0

Please use our lastest version, we have migrated our server domain.
## structure

	Android/ 			The Android project of CRISPR-X
	desktop/			The desktop version of CRISPR-X
	server/				The back-end of CRISPR-X, mainly calculation core written in C/C++
	.travis.yml         Provide Travis CI automatically build and Coveralls auto-test.

## server
[![Build Status](https://travis-ci.org/igemsoftware/UESTC-Software_2014.svg?branch=master)](https://travis-ci.org/igemsoftware/UESTC-Software_2014)
[![Coverage Status](https://coveralls.io/repos/uestc-igem-2014/CRISPR-X/badge.png?branch=master)](https://coveralls.io/r/uestc-igem-2014/CRISPR-X?branch=master)

Find potential CRISPR/Cas9 sgRNAs and give a genome-wide evaluation.

## Android

On Android, provide a form to submit model organism, RFC, target gene. Communicate with the server through JSON to get the result.

Update:
* add wiki page link.
* add history recording.
* beautify the UI design.
* Enhance the task push service.
* optimize some logic operation, more efficient.
* Analysis secondary structure and enzyme cutting sites.


Manually test passed.

## Desktop

Java program to support multiple desktop platforms. Provide the same functions as Android version.

Update:
* More friendly User Interface.
* Enable upload customized genome data.
* Analysis secondary structure and enzyme cutting sites.

Manually test passed.

