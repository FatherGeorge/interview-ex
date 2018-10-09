function mergeSort(array) {
  // change code below this line
  let leftStart = arguments[1] !== undefined ? arguments[1] : 0;
  let rightEnd = arguments[2] !== undefined ? arguments[2] : array.length-1;
  let tempArray = arguments[3] !== undefined ? arguments[3] : [];
  if(leftStart >= rightEnd) {
    return;
  }

  let middle = ((leftStart + rightEnd) / 2) >> 0;
  mergeSort(array, leftStart, middle, tempArray);
  mergeSort(array, middle+1, rightEnd, tempArray);

  let leftEnd = middle;
  let rightStart = leftEnd + 1;
  let size = rightEnd - leftStart + 1;

  let left = leftStart;
  let right = rightStart;
  let index = leftStart;

  while(left <= leftEnd && right <= rightEnd) {
    if(array[left] <= array[right]) {
      tempArray[index] = array[left];
      left++;
    } else {
      tempArray[index] = array[right];
      right++;
    }
    index++;
  }

  tempArray.splice(index, leftEnd-left+1, ...array.slice(left, leftEnd+1));
  tempArray.splice(index, rightEnd-right+1, ...array.slice(right, rightEnd+1));
  array.splice(leftStart, size, ...array.slice(leftStart, leftStart+size));
  // change code above this line
  return array;
}


let arr = [1, 4, 2, 8, 345, 123, 43, 32, 5643, 63, 123, 43, 2, 55, 1, 234, 92];
mergeSort(arr);
console.log(arr);