class NonImplementedFunctionalityException(BaseException):
    def __init__(self, message=''):
        super().__init__(message)


class UndefinedSectionException(BaseException):
    def __init__(self, message=''):
        super().__init__(message)


class WrongCommentSectionException(BaseException):
    def __init__(self, message=''):
        super().__init__(message)


class WrongImmediateException(BaseException):
    def __init__(self, message=''):
        super().__init__(message)


class WrongInstructionException(BaseException):
    def __init__(self, message=''):
        super().__init__(message)


class WrongLabelException(BaseException):
    def __init__(self, message=''):
        super().__init__(message)


class WrongOperandException(BaseException):
    def __init__(self, message=''):
        super().__init__(message)


class WrongRegistryAccessException(BaseException):
    def __init__(self, message=''):
        super().__init__(message)


class WrongRegistryIndexException(BaseException):
    def __init__(self, message=''):
        super().__init__(message)


class WrongSectionException(BaseException):
    def __init__(self, message=''):
        super().__init__(message)


class WrongStringFormatException(BaseException):
    def __init__(self, message=''):
        super().__init__(message)


class WrongSyntaxException(BaseException):
    def __init__(self, message=''):
        super().__init__(message)


class WrongVariableAssignmentException(BaseException):
    def __init__(self, message=''):
        super().__init__(message)


class SegmentationFault(BaseException):
    def __init__(self, message=''):
        super().__init__(message)


class WrongExpressionFormatException(BaseException):
    def __init__(self, message=''):
        super().__init__(message)


class WrongArrayFormatException(BaseException):
    def __init__(self, message=''):
        super().__init__(message)