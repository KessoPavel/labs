#include <stdio.h>
#include <malloc.h>
#include <math.h>
#include <xmmintrin.h>
#include <time.h>
#include <omp.h>
#include <sys/times.h>
#include <inttypes.h>
#include <stdio.h>
#include <assert.h>
#include <stdint.h>
#include <stdlib.h>
 

// l2 1024
#define BLOCK 3 
#define BLOCKSIZE 200  
#define SIZE 600

double aMatr[SIZE][SIZE];
double bMatr[SIZE][SIZE];
double resault[SIZE][SIZE] = { 0 };
double resault2[SIZE][SIZE] = { 0 };
double resault3[SIZE][SIZE] = { 0 };


void initialization() {
	for (int i = 0; i < SIZE; i++) {
		for (int j = 0; j < SIZE; j++) {
			aMatr[i][j] = (double)rand();
			bMatr[i][j] = (double)rand();
		}
	}
}

void blockMultiply();
void defaultMultiply();
void openMPMultiply();

void main() {
	srand((int)time(0));
	initialization();



	blockMultiply();
	defaultMultiply();
	openMPMultiply();
	return;
}


void blockMultiply(){
	struct tms begin, end;

	times(&begin);
	for (int io = 0; io < SIZE; io += BLOCKSIZE) {
		for (int jo = 0; jo < SIZE; jo += BLOCKSIZE) {
			for (int innero = 0; innero < SIZE; innero += BLOCKSIZE) {

				for (int inner = 0; inner < BLOCKSIZE; inner++) {
					for (int j = 0; j < BLOCKSIZE; j++) {
						for (int i = 0; i < BLOCKSIZE; i++) {

							resault[io + i][jo + j] +=
								aMatr[io + i][innero + inner] *
								bMatr[innero + inner][jo + j];
						}
					}
				}

			}
		}
	}
	times(&end);
	printf("Block multiply time :   %f;\n",(double)(end.tms_utime - begin.tms_utime));
}


void defaultMultiply(){
	struct tms begin, end;

	times(&begin);
	for (int inner = 0; inner < SIZE; inner++) {
		for (int j = 0; j < SIZE; j++) {			
			for (int i = 0; i < SIZE; i++) { 
				resault2[i][j] += aMatr[i][inner] * bMatr[inner][j];
			}
		}
	}
	times(&end);
	printf("Default multiply time : %f;\n", (double)(end.tms_utime - begin.tms_utime));

}

void openMPMultiply(){
	struct tms begin, end;
	times(&begin);

#pragma omp parallel for
	for (int inner = 0; inner < SIZE; inner++) {
		for (int j = 0; j < SIZE; j++) {
			for (int i = 0; i < SIZE; i++) {
					resault3[i][j] += aMatr[i][inner] * bMatr[inner][j];
			}
		}
	}
	times(&end);
	printf("OpenMP multiply time :  %f;\n", (double)(end.tms_utime - begin.tms_utime));
}
