

function quickSort(array) {
    // change code below this line
    let left1 = typeof arguments[1] != 'undefined' ? arguments[1] : 0;
    let right1 = typeof arguments[2] != 'undefined' ? arguments[2] : array.length - 1;
    if(left1 >= right1) return;
    let pivot = array[~~((left1 + right1) / 2)];
    
    let left = left1;
    let right = right1;
  
    while(left <= right) {
      while(array[left] < pivot) {
        left++;
      }
  
      while(array[right] > pivot) {
        right--;
      }
  
      if (left <= right) {
        let temp = array[left];
        array[left] = array[right];
        array[right] = temp;
        left++;
        right--;
      }
    }
  
    quickSort(array, left1, left-1);
    quickSort(array, left, right1);
  
  
    // change code above this line
    
  
    return array;
  }

  let array = [1, 4, 2, 8, 345, 123, 43, 32, 5643, 63, 123, 43, 2, 55, 1, 234, 92];
  quickSort(array);
  console.log(array);