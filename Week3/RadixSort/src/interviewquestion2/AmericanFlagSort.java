package interviewquestion2;

public class AmericanFlagSort {

  public static void sort(int[] a) {

    final int R = 10;

    // Count array.
    final int[] count = new int[R+1]; // extra 1 for total sum.
    for (int i = 0; i < R+1; ++i) {
      count[i] = 0;
    }

    // Count occurrences (bucket size)
    for (int i = 0; i < a.length; ++i) {
      ++count[a[i]];
    }

    // Marker array - marker[i] indicates index of input array where next entry in ith bucket is to be placed.
    final int[] marker = new int[R];
    final int[] endmarker = new int[R];

    for (int i = 1; i < R; ++i) {
      marker[i] = marker[i-1]+count[i-1]; // bucket start
      endmarker[i] = marker[i]+count[i];  // bucket end
    }

    // Exchange array - indicates bucket where the next non-native bucket
    //+member needs to go.
    final int[] exchange = new int[R];

    for(;;) {
      // Flag to indicate when were done and all buckets (1 for each
      //+member of the alphabet) are full with the correct contents.
      boolean bucketsFull = true;

      // Loop over buckets.
      for (int bucket = 0; bucket < R; ++bucket) {

        // skip over empty buckets.
        if (count[bucket] == 0) {
          continue;
        }

        // Loop over remaining elements of current bucket until
        //+we either get to the end of the bucket or encounter
        //+an entry which belongs to a different bucket.
        for (int i = marker[bucket]; i < endmarker[bucket]; ++i) {
          if ((exchange[bucket] = a[i]) != bucket) {
            // a[i] belong in a different bucket. Set flag
            // to indicate we have not finished filling this
            // bucket.
            bucketsFull = false;
            break; // continue to next bucket.
          }
          ++marker[bucket];
        }
      }

      if (bucketsFull) {
        // We're done
        break;
      } else {
        // At least 2 buckets are not full. Loop over buckets,
        //+when we encounter a non-full bucket, check its not
        //+meant to be there, and if not, then exchange the
        //+marked element with the marked element in exchange[bucket].
        //+iterate the markers to the two effected buckets
        //+whilst they have correct elements since we don't
        //+want to swap out elements that should remain.
        for (int bucket = 0; bucket < R; ++bucket) {

          // skip empty buckets.
          if (count[bucket] == 0) {
            continue;
          }

          if (marker[bucket] < endmarker[bucket] &&
              a[marker[bucket]] != bucket) {
            final int tmp = a[marker[bucket]];
            a[marker[bucket]] = a[marker[exchange[bucket]]];
            a[marker[exchange[bucket]]] = tmp;
          }

          while (marker[bucket] < endmarker[bucket] &&
                 a[marker[bucket]] == bucket) {
            ++marker[bucket];
          }

          while (marker[exchange[bucket]] < endmarker[exchange[bucket]] &&
                 a[marker[exchange[bucket]]] == exchange[bucket]) {
            ++marker[exchange[bucket]];
          }
        }
      }
    }
  }
}
