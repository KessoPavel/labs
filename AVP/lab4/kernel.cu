#include "cuda_runtime.h"
#include "device_launch_parameters.h"

#include <device_functions.h>
#include <cuda.h>
#include <device_functions.h>
#include <cuda_runtime_api.h>

#include <iostream>
#include <cstdlib>
#include <windows.h>
#include <time.h>
#define MATRIX_SIZE 10



__host__ void printMatrix(int* m)
{
	for (int i = 0; i < MATRIX_SIZE; i++) {
		for (int j = 0; j < MATRIX_SIZE; j++) {
			printf("%4d", m[i * MATRIX_SIZE + j]);
		}
		printf("\n");
	}
}

__global__ void crossAdd(int* m, int* r, int size) {
	int i = blockIdx.x * blockDim.x + threadIdx.x;
	int j = blockIdx.y * blockDim.y + threadIdx.y;

	if (!(i == 0 || i == size - 1 || j == 0 || j == size - 1))
		*(r + i * MATRIX_SIZE + j) = *(m + i * MATRIX_SIZE + j) + *(m + i * MATRIX_SIZE + j + 1) + *(m + i * MATRIX_SIZE + j - 1) + *(m + (i + 1) * MATRIX_SIZE + j) + *(m + (i - 1) * MATRIX_SIZE + j);
}

__host__ bool equal(int* m1, int* m2)
{
	bool flag = true;
	for (int i = 0; (i < MATRIX_SIZE * MATRIX_SIZE) && flag; i++) {
		flag = (*(m1 + i) == *(m2 + i));
	}
	return flag;
}


__host__  void CPU(int * m, int * r) {
	for (int i = 1; i < MATRIX_SIZE - 1; i++) {
		for (int j = 1; j < MATRIX_SIZE - 1; j++) {
			*(r + i * MATRIX_SIZE + j) = *(m + i * MATRIX_SIZE + j) + *(m + i * MATRIX_SIZE + j + 1) + *(m + i * MATRIX_SIZE + j - 1) + *(m + (i + 1) * MATRIX_SIZE + j) + *(m + (i - 1) * MATRIX_SIZE + j);
		}
	}
}

__host__ void main() {
	srand(time(NULL));

	int* matrix = (int*)malloc(sizeof(int) * MATRIX_SIZE * MATRIX_SIZE);
	int* resCPU = (int*)calloc(sizeof(int), MATRIX_SIZE * MATRIX_SIZE);
	int* resCUDA = (int*)calloc(sizeof(int), MATRIX_SIZE * MATRIX_SIZE);


	for (int i = 0; i < MATRIX_SIZE; i++) {
		for (int j = 0; j < MATRIX_SIZE; j++) {
			matrix[i * MATRIX_SIZE + j] = rand() % 100;
		}
	}

	printMatrix(matrix);

	int start_time = GetTickCount();
	CPU(matrix, resCPU);
	printf("CPU time: %i\n", GetTickCount() - start_time);

	printMatrix(resCPU);


//Cuda mem
	int* c_matrix, *c_res;
	cudaMalloc(&c_matrix, MATRIX_SIZE * MATRIX_SIZE * sizeof(int));
	cudaMalloc(&c_res, MATRIX_SIZE * MATRIX_SIZE * sizeof(int));
	
	cudaMemcpy(c_matrix, metrix,  MATRIX_SIZE * MATRIX_SIZE * sizeof(int), cudaMemcpyHostToDevice);	
	cudaMemcpy(c_res, resCUDA,  MATRIX_SIZE * MATRIX_SIZE * sizeof(int), cudaMemcpyHostToDevice);
//CUDA 
	dim3 threadsPerBlock(MATRIX_SIZE);
	dim3 numBlocks(MATRIX_SIZE * MATRIX_SIZE * sizeof(int) / threadsPerBlock.x);
	
	cudaEvent_t start, stop;
	float c_time = 0;
	cudaEventCreate(&start);
	cudaEventCreate(&stop);

	cudaEventRecord(start, 0);
	crossAdd<<<numBlocks, threadsPerBlock>>>(c_matrix, c_res);
	cudaEventRecord(stop, 0);
	
	cudaEventSynchronize(stop);
	cudaEventElapsedTime(&c_time, start, stop);
	print("CUDA time: %.0f\n", c_time);
	
	cudaMemcpy(resCUDA, c_res,  MATRIX_SIZE * MATRIX_SIZE * sizeof(int), cudaMemcpyDeviceToHost);

	cudaFree(c_matrix);
	cadaFree(c_res);

	printMAtrix(resCUDA);
}
