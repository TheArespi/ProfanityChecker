# ProfanityChecker
An implementation of the fast string matching algorithm for multiple patterns presented by Uratani and Takeda (1992) which is a combination of the Boyer-Moore and Aho-Corasick Algorithms to detect profanity in a string of text

<h4>How to use</h4>
<ol>
  <li>Build the PMM
    <ul>
      <li>This profanity checker uses a Pattern Matching Machine to determine whether there is profanity in the text. Calling <code><b>com.DesAlgo.Algorithm.PMM.buildPMM()</b></code> builds the PMM based on the profane words found at <b>pattern.txt</b>. Everytime you change the contents of the .txt file to include more words to detect, you have to call <code><b>com.DesAlgo.Algorithm.PMM.buildPMM()</b></code></li>
    </ul>
  </li>
  <li>Search for profanity in your text
    <ul>
      <li>Calling <code><b>com.DesAlgo.ProfanityChecker.ProfanityChecker.search(text)</b></code> returns a <code><b>HashMap&lt;String, List&lt;int&gt;&gt;</b></code> object that maps a profane word to the list of all the indexes where it is found. The parameter <b>text</b> is the text that you would like to check for profanity</li>
    </ul>
  </li>
</ol>
<h4>Classes and Functions</h4>
<ul>
  <li>com.DesAlgo.<b>Algorithm</b>
    <ul>
      <li>com.DesAlgo.Algorithm.<b>State</b></li>
      <ul>
        <li><b>Definition</b>: This is a State object that represents one character in the pattern matching machine</li>
      </ul>
      <li>com.DesAlgo.Algorithm.<b>PMM</b>
        <ul>
          <li><b>Definition</b>: This class includes functions that are necessary in building and loading the PMM</li>
          <li><b>Functions</b></li>
          <ul>
            <li><code><b>public static void printPMM(State startingState)</b></code></li>
            <ul>
              <li>This function prints in the console the structure of the PMM. The parameter <code><b>startingState</b></code> is the starting state of the PMM</li>
            </ul>
            <li><code><b>public static void outputPMM(State startingState)</b></code></li>
            <ul>
              <li>This function outputs the structure of the PMM into <b>output.txt</b>. The parameter <code><b>startingState</b></code> is the starting state of the PMM</li>
            </ul>
            <li><code><b>public static void buildPMM()</b></code></li>
            <ul>
              <li>This function builds the PMM and outputs it into <b>pmm.file</b></li>
            </ul>
            <li><code><b>public static State loadPMM()</b></code></li>
            <ul>
              <li>This function loads the PMM saved in the <b>pmm.file</b> and returns the starting state of the PMM</li>
            </ul>
          </ul>
      </li>
    </ul>
  </li>
  <li>com.DesAlgo.<b>ProfanityChecker</b></li>
  <ul>
    <li>com.DesAlgo.ProfanityChecker.<b>ProfanityChecker</b></li>
    <ul>
      <li><b>Definition</b>: This class contains functions that uses the PMM to find the profanity in the text given by the user</li>
      <li><b>Functions</b></li>
      <ul>
        <li><code><b>
      </ul>
    </ul>
  </ul>
</ul>
