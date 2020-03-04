(ql:quickload :drakma)

(defun getit ()
  (handler-case
      (drakma:http-request "https://raw.githubusercontent.com/StevenBlack/hosts/master/alternates/fakenews-gambling-porn-social/hosts")
    (t (c) (format t "CPU-FEJL: ~a~%" c))))
 
(defun ptrn-keep ()
  (ppcre:create-scanner
   (concatenate 'string
                "^127.0.0.1|"
                "^0.0.0.0|"
                "^255.255.255.255")))
 
(defun ptrn-remove ()
  (ppcre:create-scanner
   (concatenate 'string
                ".*reddit.*|"
                ".*redd\.it.*|"
                ".*linkedin.*|"
                "^\#.*|"
                "^$")))
 
(defun dedun ()
  (loop for line in (split-sequence:split-sequence #\linefeed (getit))
        do (if (ppcre:scan (ptrn-keep) line)
               (progn (if (not (ppcre:scan (ptrn-remove) line))
                          (progn (format t "~a" line)
                                 (terpri)))))))
 
(defun main (argv)
  (declare (ignore argv))
  (dedun))
